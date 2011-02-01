package com.parallelsymmetry.site;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {

	private static final long serialVersionUID = 549372381756415485L;

	public synchronized void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String source = request.getParameter( "source" );

		if( source == null ) {
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		// Establish the URL connection.
		URL url = new URL( source );
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout( 1000 );

		// Obtain the input stream.
		InputStream input = connection.getInputStream();
		if( input == null ) {
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}

		// Forward the header fields.
		Map<String, List<String>> fields = connection.getHeaderFields();
		for( String key : fields.keySet() ) {
			if( key != null ) {
				for( String value : fields.get( key ) ) {
					response.addHeader( key, value );
				}
			}
		}

		// Stream the data.
		byte[] buffer = new byte[4096];
		OutputStream output = response.getOutputStream();
		int count = 0;
		while( ( count = input.read( buffer ) ) >= 0 ) {
			output.write( buffer, 0, count );
		}
		output.close();
		input.close();
	}

}