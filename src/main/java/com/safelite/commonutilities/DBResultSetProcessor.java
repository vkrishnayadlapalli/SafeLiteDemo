package com.safelite.commonutilities;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface DBResultSetProcessor {

	public void process(ResultSet resultSet, long currentRow) throws SQLException;
}