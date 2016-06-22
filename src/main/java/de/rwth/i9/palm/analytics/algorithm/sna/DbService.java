package de.rwth.i9.palm.analytics.algorithm.sna;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
/**
 * Master Thesis at The Learning Technologies Research Group (LuFG Informatik 9,
 * RWTH Aachen University), Year 2014 - 2015
 * 
 * Database Connection Information
 * 
 * @author Peyman Toreini
 * @version 1.1
 */
@Service
public class DbService {
	public static Connection ConnectToDB() throws SQLException {
		Connection myConn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/nugraha?useUnicode=true&characterSetResults=utf8", "nugraha", "vFS42MmPpNqhVGXv" );
		return myConn;
	}
}