package com.wangao.dd.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlHelper {
    // 提取SQL语句中的bookid值
    public static int getBookId(String sqlStatement) {
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

    public static int [] getBooIds(String sqlStatement) {
        int[] values ={0,0} ;
        String regex = "(?i)\\bBETWEEN\\b\\s*(\\d+)\\s*AND\\s*(\\d+)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            values[0] = Integer.parseInt(matcher.group(1));
            values[1] =  Integer.parseInt(matcher.group(2));
        }
        return values;
    }

}
