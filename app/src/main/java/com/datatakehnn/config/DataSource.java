package com.datatakehnn.config;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by user on 11/11/2017.
 */


@Database(name = DataSource.NAME, version = DataSource.VERSION)
public class DataSource extends BaseModel {
    public static final String NAME = "db_datatake";
    public static final int VERSION = 2;
}