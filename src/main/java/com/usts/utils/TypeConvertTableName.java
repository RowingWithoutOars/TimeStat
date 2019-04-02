package com.usts.utils;

import java.util.ArrayList;

public enum TypeConvertTableName {
    grinding_wheel_a(1,"grinding_wheel_a"),
    grinding_wheel_b(2,"grinding_wheel_b"),
    grinding_wheel_c(3,"grinding_wheel_c"),
    grinding_wheel_d(4,"grinding_wheel_d"),
    grinding_wheel_e(5,"grinding_wheel_e"),
    grinding_wheel_f(6,"grinding_wheel_f"),
    new_line(7,"new_line"),
    old_line(8,"old_line");

    private int type;
    private String table_name;

    TypeConvertTableName(int i, String table_name) {
        this.type = i;
        this.table_name = table_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public static String getTable_Name(int i){
        for(TypeConvertTableName type:TypeConvertTableName.values()){
            if (type.getType()==i){
                return type.getTable_name();
            }
        }
        return "";
    }

    public static ArrayList<String> getAllTableName(){
        ArrayList<String> tableName = new ArrayList<>();
        for(TypeConvertTableName type:TypeConvertTableName.values()){
            tableName.add(type.getTable_name());
        }
        return tableName;
    }
}
