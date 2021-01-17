package edu.brandeis.cs12b.pa8;

import java.util.Comparator;

public class myComparator implements Comparator<Vertices>{

	@Override
	public int compare(Vertices o1, Vertices o2) {
		return o1.distance-o2.distance;
	}

}
