package com.lafetra.scott.intuition.geom;

import static java.lang.Math.*;

public class TransformMatrix {
	
	private double[][] matrix;
	
	protected TransformMatrix(double[][] matrix){//Change to private
		this.matrix = matrix;
	}
	
	/**
	 * Returns this matrix multiplied by n.
	 * @param n The amount to be multiplied by.
	 * @return This matrix multiplied by n.
	 */
	public TransformMatrix multiplyBy(double n){
		double[][] product = getArray();
		
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 3; x++){
				product[y][x] = product[y][x] * n;
			}
		}
		
		return new TransformMatrix(product);
	}
	
	/**
	 * Concatenates the two matrices.
	 * @param subject the matrix to be concatenated.
	 * @return The combined matrix
	 */
	public TransformMatrix concat(TransformMatrix subjectMatrix){
		double[][] result = new double[3][3];
		double[][] subject = subjectMatrix.getArray();
		
		for(int y = 0; y < 3; y++){//x and y are indexes of the result matrix
			for(int x = 0; x < 3; x++){
				for(int i = 0; i < 3; i++){
					result[y][x] += matrix[y][i] * subject[i][x];
				}
			}
		}
		
		return new TransformMatrix(result);
	}
	
	/**
	 * Returns the transpose matrix of this matrix.
	 * @return The transpose matrix of this matrix.
	 */
	public TransformMatrix transpose(){
		double[][] result = new double[3][3];
		
		for(int y = 0; y < 3; y++){//x and y are indexes of the result matrix
			for(int x = 0; x < 3; x++){
				result[y][x] = matrix[x][y];
			}
		}
		
		return new TransformMatrix(result);
	}
	
	/**
	 * Gets a minor matrix from this matrix.
	 * @param row Row to exclude.
	 * @param col Column to exclude.
	 * @return The minor matrix in array form.
	 */
	public double[][] getMinorOf(int row, int col){
		double[][] minor = new double[2][2];
		
		int mY = 0, mX = 0;//Minor x and y
		
		for(int y = 0; y < 3; y++){//x and y are indexes of the result matrix
			if(y == row) continue;
			
			mX = 0;
			for(int x = 0; x < 3; x++){	
				if(x == col) continue;
		
				minor[mY][mX] = matrix[y][x];
				
				mX++;
			}
			
			mY++;
		}
		
		return minor;
	}
	
	/**
	 * Returns the determinate for a minor matrix from this matrix.
	 * @param row The row to ignore.
	 * @param col The column to ignore.
	 * @return The determinate for the specified minor matrix.
	 */
	public double cofactor(int row, int col){
		double[][] minor = getMinorOf(row, col);
		
		if(row + col == 3 || row + col == 1)//Do the + - checkerboard thing
			return -( (minor[0][0]*minor[1][1]) - (minor[1][0]*minor[0][1]) );
		else
			return (minor[0][0]*minor[1][1]) - (minor[1][0]*minor[0][1]);
	}
	
	/**
	 * Returns this matrix's adjugate matrix.
	 * @return This matrix's adjugate matrix.
	 */
	public TransformMatrix adjugate(){
		double[][] cofactors = new double[3][3];
		
		for(int y = 0; y < 3; y++){//x and y are indexes of the adj matrix
			for(int x = 0; x < 3; x++){
				cofactors[y][x] = cofactor(y, x);
			}
		}
		
		return new TransformMatrix(cofactors).transpose();
	}
	
	/**
	 * Returns the determinate of this matrix.
	 * @return The determinate of this matrix.
	 */
	public double determinant(){
	
		double[][] minor;
		
		double d = 0; //determinant
		
		for(int x = 0; x < 3; x++){
			minor = getMinorOf(0, x);
			
			
			double sum = matrix[0][x]*(minor[0][0]*minor[1][1] - minor[0][1]*minor[1][0]);			
			if(x == 1) sum *= -1; //the + - + thing
			
			d += sum;
		}
		
		return d;
	}
	
	/**
	 * Returns the inverse of this matrix.
	 * @return The inverse of this matrix.
	 */
	public TransformMatrix inverse(){
		return adjugate().multiplyBy( (1.0/determinant()) );
	}

	/**
	 * Returns an array representation of this matrix.
	 * @return An array representation of this matrix.
	 */
	public double[][] getArray() {
		return matrix.clone();
	}
	
	/**
	 * Returns the 3x3 identity matrix. 
	 * @return
	 */
	public static TransformMatrix idenityMx(){
		
		double[][] m = {
				{1d, 0d, 0d},
				{0d, 1d, 0d},
				{0d, 0d, 1d}
			};
		return new TransformMatrix(m);
	}
	
	/**
	 * Returns a transformation matrix to rotating around the origin.
	 * @param degrees The amount of degrees to rotate.
	 * @return The specified transformation matrix.
	 */
	public static TransformMatrix rotateAroundOriginMx(double degrees){
		
		double t = toRadians(degrees);
		
		double[][] m = {
				{cos(t), -sin(t), 0d},
				{sin(t),  cos(t), 0d},
				{0d,          0d, 1d}
			};
		return new TransformMatrix(m);
	}
	
	/**
	 * Returns a transformation matrix for moving.
	 * @param x The amount to move the points along the x axis.
	 * @param y The amount to move the points along the y axis.
	 * @return The specified transformation matrix.
	 */
	public static TransformMatrix moveMx(double x, double y){
		
		double[][] m = {
				{1d, 0d, 0d},
				{0d, 1d, 0d},
				{x , y,  1d}
			};
		return new TransformMatrix(m);
	}
	
	/**
	 * Returns a transformation matrix for moving around a given point.
	 * @param degrees The degrees to rotate.
	 * @param rx The x cord of the point to move around.
	 * @param ry The y cord of the point to move around.
	 * @return The specified transformation matrix.
	 */
	public static TransformMatrix rotateRelToMx(double degrees, double rx, double ry){
		
		return moveMx(-rx, -ry).concat(rotateAroundOriginMx(degrees)).concat(moveMx(rx, ry));
	}
	
	/**
	 * Returns a point transformed by a matrix
	 * @param x
	 * @param y
	 * @param toApply
	 * @return
	 */
	public static Point getTransformedPoint(double x, double y, TransformMatrix toApply){
		
		double[][] m = toApply.getArray();
		
		//Simplified transform multiplication, t for temp
		double tx = m[0][0]*x + m[1][0]*y + m[2][0];
		double ty = m[0][1]*x + m[1][1]*y + m[2][1];
		
		return new Point(tx, ty);
	}
}
