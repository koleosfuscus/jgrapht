/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* ------------------------
 * SimpleGraphPathTest.java
 * ------------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Joris Kinable
 * Contributor(s):   -
 *
 * $Id$
 *
 * Changes
 * -------
 * 14-Jul-2016 : Initial revision;
 */
package org.jgrapht.graph;

import org.jgrapht.EnhancedTestCase;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;

import java.util.*;

/**
 * .
 *
 * @author Joris Kinable
 */
public class GraphWalkTest extends EnhancedTestCase {

    private Graph<Integer, DefaultEdge> completeGraph;

    public void setUp(){
        VertexFactory<Integer> vertexFactory=new IntegerVertexFactory();
        CompleteGraphGenerator<Integer, DefaultEdge> completeGraphGenerator=new CompleteGraphGenerator<>(5);
        completeGraph=new SimpleGraph<>(DefaultEdge.class);
        completeGraphGenerator.generateGraph(completeGraph, vertexFactory, new HashMap<>());
    }

    public void testEmptyPath(){
        List<GraphPath<Integer, DefaultEdge>> paths=new ArrayList<>();
        paths.add(new GraphWalk<>(completeGraph, null, null, Collections.emptyList(), 0));
        paths.add(new GraphWalk<>(completeGraph, Collections.emptyList(), 0));
        for(GraphPath<Integer, DefaultEdge> path : paths) {
            assertEquals(0, path.getLength());
            assertEquals(Collections.emptyList(), path.getVertexList());
            assertEquals(Collections.emptyList(), path.getEdgeList());
        }
    }

    public void testNonSimplePath(){
        List<Integer> vertexList= Arrays.asList(0,1,2,3,2,3,4);
        List<DefaultEdge> edgeList=new ArrayList<>();
        for(int i=0; i<vertexList.size()-1; i++)
            edgeList.add(completeGraph.getEdge(vertexList.get(i), vertexList.get(i+1)));
        GraphPath<Integer, DefaultEdge> p1=new GraphWalk<>(completeGraph, 0, 4, edgeList, 10);
        assertEquals(0, p1.getStartVertex().intValue());
        assertEquals(4, p1.getEndVertex().intValue());
        assertEquals(vertexList, p1.getVertexList());
        assertEquals(edgeList.size(), p1.getLength());
        assertEquals(10.0, p1.getWeight());

        GraphPath<Integer, DefaultEdge> p2=new GraphWalk<>(completeGraph, vertexList, 10);
        assertEquals(0, p2.getStartVertex().intValue());
        assertEquals(4, p2.getEndVertex().intValue());
        assertEquals(edgeList, p2.getEdgeList());
        assertEquals(edgeList.size(), p2.getLength());
        assertEquals(10.0, p2.getWeight());
    }

    private class IntegerVertexFactory implements VertexFactory<Integer>{
        int count;

        @Override
        public Integer createVertex() {
            return count++;
        }
    }
}
