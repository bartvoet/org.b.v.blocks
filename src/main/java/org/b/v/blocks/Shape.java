package org.b.v.blocks;

public interface Shape {
	boolean isNeighbour(Shape shape);
	void draw(ShapePainter painter);
	
	//recursief algoritme?
	//gaat zijn buren af
	//die gaan hun buren af behalve de aanroepende
	//als de shapepainter een shape tegenkomt die getekend is maar overlapt klopt er iets nniet
	
}
