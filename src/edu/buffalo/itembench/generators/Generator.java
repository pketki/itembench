package edu.buffalo.itembench.generators;

import java.io.IOException;
import java.util.Date;

/**
 * Created by abhinit on 11/10/15.
 */
public interface Generator {

	public String getNextString(int length);

	public String getNextString(String valuePool) throws IOException;

	public int getNextInt();

	public int getNextInt(int start, int end);

	public double getNextDouble();

	public double getNextDouble(double start, double end);

	public Date getNextDate();

	public Date getNextDate(Date start, Date end, int interval);

}
