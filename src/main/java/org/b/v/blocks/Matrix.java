package org.b.v.blocks;

import java.util.LinkedList;

public class Matrix<T> {
	
	private LinkedList<LinkedList<T>> elements = new LinkedList<LinkedList<T>>();
	private int columns;
	
	public Matrix() {
		columns=0;
	}
	
	public boolean existsElementAt(int x,int y) {
		return false;
	}
	
	public T getElementAt(int x,int y) {
		if(x >= this.columns || y >= this.elements.size() ) {
			return null;
		}
		LinkedList<T> row = getRow(y);
		if(x >= row.size()) {
			return null;
		}
		return row.get(x);
	}
	
	private LinkedList<T> getRow(int y) {
		if(y + 1 > elements.size()) {
			for(int i=0;i < (y + 1 - elements.size());i++) {
				this.elements.addLast(new LinkedList<T>());
			}
		}
		return this.elements.get(y);
	}
	
	private void setElementAtColumn(int x,T t,LinkedList<T> row) {
		if(x >= this.columns) {
			this.columns=x+1;
		}
		if(this.columns >= row.size()) {
			adaptSize(row);
		}
		row.set(x,t);
	}

	private void adaptSize(LinkedList<T> row) {
		for(int i=0;i < (this.columns - row.size());i++) {
			row.addLast(null);
		}
	}
	
	public Matrix<T> setElementAt(T t,int x,int y) {
		setElementAtColumn(x,t,getRow(y));	
		return this;
	}
	
	public void insertColumAtStart() {
		this.columns++;
		for(LinkedList<T> row : this.elements) {
			row.addFirst(null);
		}
		
	}
}
