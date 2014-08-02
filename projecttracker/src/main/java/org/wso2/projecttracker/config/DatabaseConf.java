/*
 * Copyright 2014-2015 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.projecttracker.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConf {
    private DatabaseConf() {
    }

    static int turn = 0;
    private static Connection connection1 = null;
    private static Connection connection2 = null;

    public static Connection getConnection() {

        if (turn == 0) {
            turn=1;
            InitialContext context;
            try {
                context = new InitialContext();
                String dataSourceName = getProperty("datasource.name");
                DataSource dataSource = (DataSource) context
                        .lookup(dataSourceName);
                connection1 = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection1;
        } else{
            turn=0;
            InitialContext context;
            try {
                context = new InitialContext();
                String dataSourceName = getProperty("datasource.name");
                DataSource dataSource = (DataSource) context
                        .lookup(dataSourceName);
                connection2 = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection2;
        }
    }

    private static String getProperty(String value) {
        String dataSource = null;
        try {
            Properties properties = new Properties();
            properties.load(DatabaseConf.class
                    .getResourceAsStream("/datasource.properties"));
            dataSource = properties.getProperty(value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}