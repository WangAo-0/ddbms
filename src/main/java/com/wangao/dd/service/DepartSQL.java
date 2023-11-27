package com.wangao.dd.service;
import com.wangao.dd.protohelper.MyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class DepartSQL {
    @Autowired
    private DataBaseService dataBaseService;
    @Autowired
    private ClientRemoteImpl clientRemote;

    @Value("${server.port}")
    private String port;

    String serverAddress="127.0.0.1";
    int port1=9507;
    int port2=9508;
    int port3=9509;


    public List<Map<String, Object>> sql(String sqlStatement) {
        String sqlStatement0 = "SELECT * FROM Books WHERE book_id = 1";
        String sqlStatement1 = "SELECT * FROM Books WHERE book_id BETWEEN 10 AND 200";
        String sqlStatement2 = "SELECT * FROM Books WHERE book_id NOT BETWEEN 50 AND 201";
        String sqlStatement3 = "SELECT * FROM Books";
        String sqlStatement6 = "INSERT INTO Books (book_id, title, author_id, ISBN, published_date, genre) VALUES (20, 2, 3, 4,\"2013-11-09\", 6)";
        String sqlStatement7 = "INSERT INTO Users (user_id, username, email, address) VALUES (2 ,2 , 2 , 2)";
        String sqlStatement8 = "UPDATE Users SET  username = 3, email = 3,address = 3  WHERE user_id = 2";
        return processBookIdQuery(sqlStatement);
    }


    // 判断SQL语句是否包含bookid的查询
    private  boolean containsBookIdQuery(String sqlStatement) {
        // 使用正则表达式检测是否包含类似"bookid = ..."的查询条件
        String regex = "\\bbook_id\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sqlStatement);

        return matcher.find();
    }

    // 处理包含bookid的查询
    private List<Map<String, Object>> processBookIdQuery(String sqlStatement) {
        // 具体根据SQL语句的内容进行处理
        if (sqlStatement.contains("book_id BETWEEN")) {
            // 处理范围查询
            return processRangeQuery(sqlStatement);
        } else if (sqlStatement.contains("book_id NOT BETWEEN")) {
            // 处理范围外查询
            return processNotBetweenQuery(sqlStatement);
        }
        /*else if (sqlStatement.contains("INNER JOIN Borrowings")) {
            // 处理已借阅的书籍查询或特定用户借阅的书籍查询
            processBorrowingsQuery(sqlStatement);
        } else if (sqlStatement.contains("COUNT(book_id)")) {
            // 处理统计每个作者的书籍数量查询
            processAuthorCountQuery(sqlStatement);
        } */
          else if(sqlStatement.contains("book_id =")){
//            int bookId = extractBookId(sqlStatement);
            // 根据bookid查询不同站点
            return querySiteById(sqlStatement);
        } else if(sqlStatement.contains("INSERT")&&sqlStatement.contains("book_id")){
            int bookId = insertDepart(sqlStatement);
            bookId=bookId/100+1;
            return recogServer(bookId,sqlStatement);
        }
          /*else if(sqlStatement.contains("UPDATE")){
            return querySiteById(sqlStatement);
        } */
        else {
            // 其他情况，根据实际需求进行处理
            System.out.println("SQL query: " + sqlStatement);
            System.out.println("search from three servers" );
            List<Map<String, Object>> servers = new ArrayList<>();
            servers.addAll(recogServer(1,sqlStatement));
            servers.addAll(recogServer(2,sqlStatement));
            servers.addAll(recogServer(3,sqlStatement));
            return  servers;
        }
    }

    // 提取SQL语句中的bookid值
    private  int extractBookId(String sqlStatement) {
        // 简单示例：假设bookid是整数并在等号后面
        String regex = "\\bbook_id\\s*=\\s*(\\d+)\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            // 如果无法提取bookid值，这里需要根据实际情况处理
            throw new IllegalArgumentException("Unable to extract bookid from SQL statement");
        }
    }

    // 查询所有站点的示例函数，需要根据实际情况替换为grpc生成的函数调用
    private static void queryAllSites() {
        System.out.println("Querying all sites...");
        // 调用grpc生成的函数查询站点
        // site1Query();
        // site2Query();
        // site3Query();
    }

    private List<Map<String, Object>> transMap(List<MyMap> dataList){
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            MyMap myMap = dataList.get(i);
            // 将 MyMap 转换为 Map<String, Object>
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < myMap.getEntryCount(); j++) {
                map.put(myMap.getEntry(j).getKey(), myMap.getEntry(j).getValue());
            }
            // 将 Map 添加到 List
            mapList.add(map);
        }
        return  mapList;
    }

    private List<Map<String, Object>> recogServer(int serverNum,String sqlStatement){
        int affects=0;
        List<Map<String, Object>> single=new ArrayList<>();
        Object mymap;
        int recogPort = Integer.parseInt(port);
        recogPort=recogPort-9516;
        if (recogPort==serverNum){
            System.out.println("Search in local database");
            if (sqlStatement.contains("UPDATE")||sqlStatement.contains("INSERT")){
                affects=dataBaseService.update(sqlStatement);
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Affects", affects);
                single.add(innerMap);
            } else {
                single=dataBaseService.select(sqlStatement);
            }
        } else if (serverNum==1){
            System.out.println("Remote Search in database1");
            mymap=clientRemote.doSelectRemote(serverAddress,port1,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        } else if (serverNum==2){
            System.out.println("Remote Search in database2");
            mymap=clientRemote.doSelectRemote(serverAddress,port2,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        } else if (serverNum==3){
            System.out.println("Remote Search in database3");
            mymap=clientRemote.doSelectRemote(serverAddress,port3,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        }
        return  single;
    }

    private List<Map<String, Object>> recogServerInsert(int serverNum,String sqlStatement){
        int affects=0;
        List<Map<String, Object>> single=new ArrayList<>();
        Object mymap;
        int recogPort = Integer.parseInt(port);
        recogPort=recogPort-9516;
        if (recogPort==serverNum){
            System.out.println("Search in local database");
            affects=dataBaseService.update(sqlStatement);
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("Affects", affects);
            single.add(innerMap);
        } else if (serverNum==1){
            System.out.println("Remote Search in database1");
            mymap=clientRemote.doSelectRemote(serverAddress,port1,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        } else if (serverNum==2){
            System.out.println("Remote Search in database2");
            mymap=clientRemote.doSelectRemote(serverAddress,port2,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        } else if (serverNum==3){
            System.out.println("Remote Search in database3");
            mymap=clientRemote.doSelectRemote(serverAddress,port3,sqlStatement);
            if (mymap==null){
                return single;
            }
            single=transMap((List<MyMap>) mymap);
        }
        return  single;
    }

    // 根据bookid查询不同站点的示例函数，需要根据实际情况替换为grpc生成的函数调用
    private List<Map<String, Object>> querySiteById(String sqlStatement) {
        int bookId = extractBookId(sqlStatement);
        List<Map<String, Object>> select = new ArrayList<>();
        System.out.println("Querying: " + bookId);
        if(bookId >= 1&&bookId <= 100){
            System.out.println("Search on server 1 :" + bookId);
            select=recogServer(1,sqlStatement);
        } else if (bookId >= 101&&bookId <= 200) {
            System.out.println("Search on server 2 :" + bookId);
            select=recogServer(2,sqlStatement);
        } else if (bookId >= 201&&bookId <= 300) {
            System.out.println("Search on server 3 :" + bookId);
            select=recogServer(3,sqlStatement);
        }
        return select;
        // 根据bookid调用grpc生成的函数查询站点
    }

    private List<Map<String, Object>> querySiteByIdUpdate(String sqlStatement) {
        int bookId = extractBookId(sqlStatement);
        List<Map<String, Object>> select = new ArrayList<>();
        System.out.println("Querying: " + bookId);
        if(bookId >= 1&&bookId <= 100){
            System.out.println("Search on server 1 :" + bookId);
            select=recogServerInsert(1,sqlStatement);
        } else if (bookId >= 101&&bookId <= 200) {
            System.out.println("Search on server 2 :" + bookId);
            select=recogServerInsert(2,sqlStatement);
        } else if (bookId >= 201&&bookId <= 300) {
            System.out.println("Search on server 3 :" + bookId);
            select=recogServerInsert(3,sqlStatement);
        }
        return select;
    }

    private  List<Map<String, Object>> processRangeQuery(String sqlStatement) {
        List<Map<String, Object>> rangeList=new ArrayList<>();

        // 具体处理范围查询的逻辑
        System.out.println("Processing range query: " + sqlStatement);

        // 提取BETWEEN关键字后的范围值
        String[] values = extractRangeValues(sqlStatement);
        if (values.length == 2) {
            int lowerBound = Integer.parseInt(values[0]);
            int upperBound = Integer.parseInt(values[1]);

            // 处理范围查询的逻辑
            if (isIntersecting(lowerBound, upperBound, 1, 100)) {
                // 创建子查询1
                String subQuery1 = createSubQueryWithRange(sqlStatement, Math.max(lowerBound, 1), Math.min(upperBound, 100));
                System.out.println("Subquery 1: " + subQuery1);
                // 处理子查询1的逻辑
                rangeList.addAll(recogServer(1,subQuery1));
            }

            if (isIntersecting(lowerBound, upperBound, 101, 200)) {
                // 创建子查询2
                String subQuery2 = createSubQueryWithRange(sqlStatement, Math.max(lowerBound, 101), Math.min(upperBound, 200));
                System.out.println("Subquery 2: " + subQuery2);
                // 处理子查询2的逻辑
                rangeList.addAll(recogServer(2,subQuery2));
            }

            if (isIntersecting(lowerBound, upperBound, 201, 300)) {
                // 创建子查询3
                String subQuery3 = createSubQueryWithRange(sqlStatement, Math.max(lowerBound, 201), Math.min(upperBound, 300));
                System.out.println("Subquery 3: " + subQuery3);
                // 处理子查询3的逻辑
                rangeList.addAll(recogServer(3,subQuery3));
            }
        } else {
            System.out.println("Invalid range values");
        }
        return rangeList;
        // ...
    }

    private List<Map<String, Object>> processNotBetweenQuery(String sqlStatement) {
        List<Map<String, Object>> nbList=new ArrayList<>();

        // 具体处理范围外查询的逻辑
        System.out.println("Processing NOT BETWEEN query: " + sqlStatement);

        // 提取NOT BETWEEN关键字后的范围值
        String[] values = extractRangeValues(sqlStatement);

        if (values.length == 2) {
            int lowerBound = Integer.parseInt(values[0]);
            int upperBound = Integer.parseInt(values[1]);

            // 处理范围外查询的逻辑
            if (!isFullyCovered(lowerBound, upperBound, 1, 100)) {
                // 创建未覆盖部分的子查询1
                List<Map<String, Integer>> remainingRanges=remainRange(lowerBound,upperBound,1,100);
                // 处理子查询1的逻辑
                List<String> queries = createQueries(sqlStatement,remainingRanges);
                for (String query : queries) {
                    // 在这里调用查询操作
                    System.out.println("Executing query: " + query);
                    // 这里你可以添加调用执行查询的逻辑
                    nbList.addAll(processRangeQuery(query));
                }
            }

            if (!isFullyCovered(lowerBound, upperBound, 101, 200)) {
                // 创建未覆盖部分的子查询2
                List<Map<String, Integer>> remainingRanges=remainRange(lowerBound,upperBound,101,200);
                // 处理子查询2的逻辑
                List<String> queries = createQueries(sqlStatement,remainingRanges);
                for (String query : queries) {
                    // 在这里调用查询操作
                    System.out.println("Executing query: " + query);
                    // 这里你可以添加调用执行查询的逻辑
                    nbList.addAll(processRangeQuery(query));
                }
            }

            if (!isFullyCovered(lowerBound, upperBound, 201, 300)) {
                // 创建未覆盖部分的子查询3
                List<Map<String, Integer>> remainingRanges=remainRange(lowerBound,upperBound,201,300);
                // 处理子查询3的逻辑
                List<String> queries = createQueries(sqlStatement,remainingRanges);
                for (String query : queries) {
                    // 在这里调用查询操作
                    System.out.println("Executing query: " + query);
                    // 这里你可以添加调用执行查询的逻辑
                    nbList.addAll(processRangeQuery(query));
                }
            }
        } else {
            System.out.println("Invalid range values");
        }
        return nbList;
        // ...
    }

    // 提取BETWEEN或NOT BETWEEN关键字后的范围值
    private static String[] extractRangeValues(String sqlStatement) {
        String[] values = new String[2];
        String regex = "(?i)\\bBETWEEN\\b\\s*(\\d+)\\s*AND\\s*(\\d+)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            values[0] = matcher.group(1);
            values[1] = matcher.group(2);
        }
        return values;
    }

    private static boolean isIntersecting(int lowerBound1, int upperBound1, int lowerBound2, int upperBound2) {
        return !(upperBound1 < lowerBound2 || lowerBound1 > upperBound2);
    }

    private static String createSubQueryWithRange(String sqlStatement, int lower, int upper) {
        // 替换原始 SQL 语句中的范围值
        return sqlStatement.replaceAll("(?i)\\bbook_id\\b\\s*between\\s*\\d+\\s*and\\s*\\d+", "book_id BETWEEN " + lower + " AND " + upper);
    }

    private static boolean isFullyCovered(int lowerBound1, int upperBound1, int lowerBound2, int upperBound2) {
        return lowerBound1 <= lowerBound2 && upperBound1 >= upperBound2;
    }


    private static List<Map<String, Integer>> remainRange(int lowerBound, int upperBound, int rangeStart, int rangeEnd) {
        List<Map<String, Integer>> remainingRanges = new ArrayList<>();

        if (lowerBound > rangeStart && upperBound < rangeEnd) {
            // 如果输入的范围在rangeStart和rangeEnd中间，返回两个区间
            Map<String, Integer> firstRange = new HashMap<>();
            firstRange.put("start", rangeStart);
            firstRange.put("end", lowerBound - 1);
            remainingRanges.add(firstRange);

            Map<String, Integer> secondRange = new HashMap<>();
            secondRange.put("start", upperBound + 1);
            secondRange.put("end", rangeEnd);
            remainingRanges.add(secondRange);
        } else if ((lowerBound>rangeEnd)||(upperBound<rangeStart)) {
            Map<String, Integer> firstRange = new HashMap<>();
            firstRange.put("start", rangeStart);
            firstRange.put("end", rangeEnd);
            remainingRanges.add(firstRange);
        } else if ((lowerBound>rangeStart)&&(upperBound>rangeEnd)) {
            Map<String, Integer> firstRange = new HashMap<>();
            firstRange.put("start", rangeStart);
            firstRange.put("end", lowerBound-1);
            remainingRanges.add(firstRange);
        } else if ((lowerBound<rangeStart)&&(upperBound<rangeEnd)) {
            Map<String, Integer> firstRange = new HashMap<>();
            firstRange.put("start", upperBound+1);
            firstRange.put("end", rangeEnd);
            remainingRanges.add(firstRange);
        }


        return remainingRanges;
    }

    private static List<String> createQueries(String originalStatement, List<Map<String, Integer>> remainingRanges) {
        List<String> queries = new ArrayList<>();

        for (Map<String, Integer> range : remainingRanges) {
            int start = range.get("start");
            int end = range.get("end");

            // 根据给定的范围和原始语句创建查询语句
            String query = createQueryWithRange(originalStatement, start, end);

            // 添加到查询语句列表
            queries.add(query);
        }

        return queries;
    }

    private static String createQueryWithRange(String originalStatement, int start, int end) {
        // 将原始语句中的 NOT BETWEEN 修改为 BETWEEN，并替换区间
        String modifiedStatement = originalStatement.replaceAll("(?i)\\bbook_id\\b\\s*not\\s*between\\s*\\d+\\s*and\\s*\\d+",
                "book_id BETWEEN " + start + " AND " + end);

        // 这里你可以根据实际情况修改，例如，如果不是 SQL 查询语句，可以直接返回 modifiedStatement
        return  modifiedStatement;
    }

    private int insertDepart(String sqlStatement) {
//        String sqlStatement = "INSERT INTO Books (title, book_id, author) VALUES ('The Catcher in the Rye', 2, 'J.D. Salinger');";

        int x=0;
        // 使用正则表达式提取 book_id 在括号内的位置
        Pattern pattern = Pattern.compile("\\(([^)]*book_id[^)]*)\\)");
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            String match = matcher.group(1);

            // 判断 book_id 在括号内的位置
            String[] columnNames = match.split(",\\s*");
            int bookIdIndex = -1;
            for (int i = 0; i < columnNames.length; i++) {
                if (columnNames[i].contains("book_id")) {
                    bookIdIndex = i;
                    break;
                }
            }

            // 提取 VALUES 括号内的内容
            pattern = Pattern.compile("VALUES \\((.*?)\\)");
            matcher = pattern.matcher(sqlStatement);

            if (matcher.find()) {
                String valuesContent = matcher.group(1);

                // 将括号内的内容拆分为数组
                String[] valuesArray = valuesContent.split(", ");


                if (bookIdIndex >= 0 && bookIdIndex < valuesArray.length) {
                    String result = valuesArray[bookIdIndex];
                    System.out.println("提取的第 " + (bookIdIndex + 1) + " 个值为: " + result);
                    x= Integer.parseInt(result);
                } else {
                    System.out.println("输入的 x 超出了范围");
                }
            }
            return x;
        }
        return x;
    }
}
