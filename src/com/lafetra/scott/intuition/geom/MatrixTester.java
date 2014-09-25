package com.lafetra.scott.intuition.geom;

import java.util.Arrays;

public class MatrixTester {
	public static void main(String[] args) {
		double[][] matrix1 = {
				{1d, 2d, 3d},
				{0d, 1d, 4d},
				{5d, 6d, 0d}
			};
		TransformMatrix m1 = new TransformMatrix(matrix1);
		
		System.out.println(Arrays.deepToString(m1.getMinorOf(0, 1)));
		
		System.out.println(Arrays.deepToString(m1.adjugate().getArray()));
		
		System.out.println(m1.determinant());
		
		System.out.println(Arrays.deepToString(m1.inverse().getArray()));

	}

}
