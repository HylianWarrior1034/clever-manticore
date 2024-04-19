ChainShape : this is a chain: free form sequence of line segments. Two-sided collision: inside and outside collision. The line segements are the `EdgeShape` class.
`Edge Shape` has two must vertices for start and finish.

What is the radius of the chain? Is it the thickness of the line segments?

## NOTE: None of the functions clear up what the parameters can be. Inside the code are assert statements. I pooled this info from them.

Fields inherited from Shape :
    `ShapeType m_type`: ShapeType.CHAIN
    `float m_radius`: neg, 0, pos

Empty chain shape has these fields:
    `Vec2[] m_vertices` :  These are the vertices the chain is made up of. An empty chain will have none. A chain with a single segment will have two. A chain with three will have four. Chain of N edges will have N+1 vertices for all N>0.
    `int m_count` : keeps track on the number of segments in the chain
    `final Vec2 m_prevVertex`: ??? this is set for loops
    `final Vec2 m_nextVertex`: ??? this is set for loops
    `boolean m_hasPrevVertex` : clear
    `boolean m_hasNexVertex` : clear
    `final EdgeShape pool0` : ??? this may be the resulting normal for raycasting function ??? maybe 

`getChildEdge(EdgeShape edge, int index)`: Get a child EdgeShape segment from the chain. If the chain has one segment, there is no child edge. Since number of segments is `m_count`, index of child has to be 1 less. Index has to be non-negative. 

`getChildCount()` : return the number of segments that do not begin at the start of the chain.

`boolean testPoint(Transform xf, Vec2 p)` will always return false because this is a Concave shape. (Even if the chain loops, the area inside is not counted to be part of the shape).

`boolean raycast(RayCastOutput output, RayCastInput input,Transform xf, int childIndex)` : See if ray input intersects with a child segment of the chain shape. If it does, return true.

`computeAABB(AABB aabb, Transform xf, int childIndex)` : implement a transformation on a child segment of a chain shape and compute the axis aligned bounding box for it.

`computeMass(MassData massData, float density)` : this function will return the same massData regardless of density input.

`Shape clone()` : make a copy object of the chain shape.

`createLoop(Vec2[] vertices, int count)` : Create loop out of given vertices data; count has to be at least three: You can make a loop with three straight edges or more. Chain data has to be empty: overwritten with the loop data.
Parameter `vertices` should be a copy of `m_vertices` outside of this function. `m_hasPrevVertex` and `m_hasNextVertex` are set to true. Loop has 1 more vertex than a chain: the end vertex.

`createChain(Vec2[] vertices, int count)` : Chain data has to be empty. Create a chain from an array of vertices: there has to be at least two vertices to make a chain. `m_hasPrevVertex` and `m_hasNextVertex` are set to false.