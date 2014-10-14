package com.android.pennaed.campussafety;

/*
 * Class uses geometry to check if a point lies inside of a polygon
 */
public class PointInPolygon {

	public static double TWOPI = 2 * Math.PI;
	private double[] lat_array;
	private double[] lng_array;

	public PointInPolygon(double[] lat_array, double[] lng_array) {
		this.lat_array = lat_array;
		this.lng_array = lng_array;
	}

	public boolean coordinate_is_inside_polygon(double latitude,
	                                            double longitude) {
		int i;
		double angle = 0;
		double point1_lat;
		double point1_lng;
		double point2_lat;
		double point2_long;
		int n = lat_array.length;

		for (i = 0; i < n; i++) {
			point1_lat = lat_array[i] - latitude;
			point1_lng = lng_array[i] - longitude;
			point2_lat = lat_array[(i + 1) % n] - latitude;
			point2_long = lng_array[(i + 1) % n] - longitude;
			angle += Angle2D(point1_lat, point1_lng, point2_lat, point2_long);
		}

		if (Math.abs(angle) < Math.PI)
			return false;
		else
			return true;
	}

	public double Angle2D(double y1, double x1, double y2, double x2) {
		double dtheta, theta1, theta2;

		theta1 = Math.atan2(y1, x1);
		theta2 = Math.atan2(y2, x2);
		dtheta = theta2 - theta1;
		while (dtheta > Math.PI)
			dtheta -= TWOPI;
		while (dtheta < -Math.PI)
			dtheta += TWOPI;

		return (dtheta);
	}

}
