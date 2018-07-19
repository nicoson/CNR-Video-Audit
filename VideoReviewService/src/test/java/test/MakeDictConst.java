package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import cn.qiniu.util.FileUtils;
import cn.qiniu.util.StringUtil;

public class MakeDictConst {
    public static void main(String[] args) {
    	
        Connection conn;
        Statement stmt;
        ResultSet rs;
        String url = "jdbc:mysql://192.168.0.190:3306/qiniu";
        String sql = "select VALUE,LABEL,TYPE,DESCRIPTION,REMARKS from SYS_DICT order by TYPE, VALUE";
        StringBuilder fileContent = new StringBuilder();
        String filePath = "D:\\DictConst.java";
        String dict_type = "";
        String dict_code = "";
        //定义不必要生成的字典类型
        String[] except_type = {"gen_java_type","gen_query_type"};
        List<String> exceptList = Arrays.asList(except_type);
        
//        try {  
//            Class.forName("com.mysql.jdbc.Driver");  
//        } catch (ClassNotFoundException e) {  
//            System.out.println("无法加载驱动");  
//        }  
        try {
            // 连接数据库
            conn = DriverManager.getConnection(url, "root", "root");
            // 建立Statement对象
            stmt = conn.createStatement();
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            
            //定义文件格式
            fileContent.append("package com.deloitte.common.config;");
            fileContent.append(" ");
            fileContent.append("\n");
            fileContent.append("/**").append("\n");
            fileContent.append(" * 字典常量类").append("\n");
            fileContent.append(" * @author ").append("\n");
            fileContent.append(" * @version 2017-04-21").append("\n");
            fileContent.append(" */").append("\n");
            fileContent.append("public class DictConst {").append("\n");
            
            //循环生成字典常量
            while (rs.next()) {
            	//获取字典类型
            	String type = rs.getString("TYPE");
            	//获取字典类型描述
            	String description = rs.getString("DESCRIPTION");
            	//获取字典value
            	String value = rs.getString("VALUE");
            	//获取字典value描述
            	String label = rs.getString("LABEL");
            	//获取字典remarks
            	String remarks = rs.getString("REMARKS");
            	//设置字典code,如果remarks不为空则沿用remarks，否则沿用value
            	dict_code = !StringUtil.isNullOrEmpty(remarks)?remarks:value;
            	//例外验证
            	if(exceptList.contains(type)) {
            		continue;
            	}
        		//相同字典类型
        		if(dict_type.equals(type)) {
                	fileContent.append("        /**" + label + "**/").append("\n");
                	fileContent.append("		public static final String " + type.toUpperCase()  + "_" + dict_code  + " = \"" + value + "\";").append("\n");
        		} else {
        			if(!StringUtil.isNullOrEmpty(dict_type)) {
        				fileContent.append("	}").append("\n");
        				fileContent.append("\n");
        			}
        			dict_type = type;
            		fileContent.append("	/**").append("\n");
                	fileContent.append("	 * " + description).append("\n");
                	fileContent.append("	 */").append("\n");
                	fileContent.append("	public static final String TYPE_" + type.toUpperCase() + " = \"" + type + "\";").append("\n");
                	fileContent.append("	public static final class " + type.toUpperCase()  + " {").append("\n");
                	fileContent.append("        /**" + label + "**/").append("\n");
                	fileContent.append("		public static final String " + type.toUpperCase()  + "_" + dict_code  + " = \"" + value + "\";").append("\n");
        		}
            }
            fileContent.append("	}").append("\n");
            fileContent.append("}");
            //生成字典文件
        	FileUtils.writeToFile(filePath, fileContent.toString(), "utf-8", true);
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
    }
}