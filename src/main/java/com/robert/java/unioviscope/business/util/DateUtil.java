package com.robert.java.unioviscope.business.util;

import java.util.Date;

/**
 * Clase de utilidades para fechas.
 * 
 * @author Robert Ene
 */
public class DateUtil {

	public static Boolean overlaps(Date startA, Date endA, Date startB, Date endB) {
		if (startA == null || endA == null || startB == null || endB == null)
			return false;

		return (startA.getTime() < endB.getTime()) && (startB.getTime() < endA.getTime())
				&& (startA.getTime() < endA.getTime()) && (startB.getTime() < endB.getTime())
				|| (startA.getTime() < endB.getTime()) && (startA.getTime() < endA.getTime())
						&& (startB.getTime() < endA.getTime()) && (startB.getTime() < endB.getTime());
	}
}
