package com.infunity.isometricgame.shared.utils.dermetfan;

/** Copyright 2014 Robin Stumm (serverkorken@gmail.com, http://dermetfan.net)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.ShortArray;

import java.util.Arrays;
import java.util.Comparator;

import static com.infunity.isometricgame.shared.utils.dermetfan.ArrayUtils.wrapIndex;
import static com.infunity.isometricgame.shared.utils.dermetfan.CustomMathUtils.*;

/** Provides some useful methods for geometric calculations. Note that many methods return the same array instance so make a copy for subsequent calls.
 *  @author dermetfan */
public abstract class GeometryUtils {

    /** a {@link com.badlogic.gdx.math.Vector2} for temporary usage */
    private static final Vector2 vec2_0 = new Vector2(), vec2_1 = new Vector2();

    /** a temporary array */
    private static Vector2[] tmpVecArr;

    /** a temporary array */
    private static float[] tmpFloatArr;

    /** @return if the given point is between a and b (exclusive)
     *  @see #between(com.badlogic.gdx.math.Vector2, com.badlogic.gdx.math.Vector2, com.badlogic.gdx.math.Vector2, boolean) */
    public static boolean between(Vector2 point, Vector2 a, Vector2 b) {
        return between(point, a, b, false);
    }

    /** @param point the point to test
     *  @param a the first point of the segment
     *  @param b the second point of the segment
     *  @param inclusive if the given point is allowed to be equal to min or maxs
     *  @return if the given point lies on a line with and between the given points
     *  @see CustomMathUtils#between(float, float, float) */
    public static boolean between(Vector2 point, Vector2 a, Vector2 b, boolean inclusive) {
        return det(a.x, a.y, point.x, point.y, b.x, b.y) == 0 && CustomMathUtils.between(point.x, a.x, b.x, inclusive) && CustomMathUtils.between(point.y, a.y, b.y, inclusive);
    }

    /** @param vector the {@link com.badlogic.gdx.math.Vector2} which components to set to their absolute value
     *  @return the given vector with all components set to its absolute value
     *  @see Math#abs(float) */
    public static Vector2 abs(Vector2 vector) {
        vector.x = Math.abs(vector.x);
        vector.y = Math.abs(vector.y);
        return vector;
    }

    /** @see #abs(com.badlogic.gdx.math.Vector2) */
    public static Vector3 abs(Vector3 vector) {
        vector.x = Math.abs(vector.x);
        vector.y = Math.abs(vector.y);
        vector.z = Math.abs(vector.z);
        return vector;
    }

    /** @param vertices the vertices to add the given values to
     *  @param x the x value to add
     *  @param y the y value to add */
    public static void add(Vector2[] vertices, float x, float y) {
        for(Vector2 vertice : vertices)
            vertice.add(x, y);
    }

    /** @see #add(com.badlogic.gdx.math.Vector2[], float, float) */
    public static void sub(Vector2[] vertices, float x, float y) {
        add(vertices, -x, -y);
    }

    /** @see #add(com.badlogic.gdx.math.Vector2[], float, float) */
    public static void add(float[] vertices, float x, float y) {
        for(int i = 1; i < vertices.length; i += 2) {
            vertices[i - 1] += x;
            vertices[i] += y;
        }
    }

    /** @see #add(com.badlogic.gdx.math.Vector2[], float, float) */
    public static void sub(float[] vertices, float x, float y) {
        add(vertices, -x, -y);
    }

    /** @return a Vector2 representing the size of a rectangle containing all given vertices */
    public static Vector2 size(Vector2[] vertices, Vector2 output) {
        return output.set(width(vertices), height(vertices));
    }

    /** @see #size(com.badlogic.gdx.math.Vector2[], com.badlogic.gdx.math.Vector2) */
    public static Vector2 size(Vector2[] vertices) {
        return size(vertices, vec2_0);
    }

    /** @return the amplitude from the min x vertice to the max x vertice */
    public static float width(Vector2[] vertices) {
        return amplitude(filterX(vertices));
    }

    /** @return the amplitude from the min y vertice to the max y vertice */
    public static float height(Vector2[] vertices) {
        return amplitude(filterY(vertices));
    }

    /** @see #width(com.badlogic.gdx.math.Vector2[]) */
    public static float width(float[] vertices) {
        return amplitude(filterX(vertices));
    }

    /** @see #height(com.badlogic.gdx.math.Vector2[]) */
    public static float height(float[] vertices) {
        return amplitude(filterY(vertices));
    }

    /** @return the amplitude of the min z vertice to the max z vertice */
    public static float depth(float[] vertices) {
        return amplitude(filterZ(vertices));
    }

    /** @return the x values of the given vertices */
    public static float[] filterX(Vector2[] vertices, float[] output) {
        if(output == null || output.length != vertices.length)
            output = new float[vertices.length];
        for(int i = 0; i < output.length; i++)
            output[i] = vertices[i].x;
        return output;
    }

    /** @see #filterX(com.badlogic.gdx.math.Vector2[], float[]) */
    public static float[] filterX(Vector2[] vertices) {
        return filterX(vertices, tmpFloatArr);
    }

    /** @param vertices the vertices in [x, y, x, y, ...] order
     *  @see #filterX(com.badlogic.gdx.math.Vector2[]) */
    public static float[] filterX(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, -1, 2, output);
    }

    /** @see #filterX(float[], float[]) */
    public static float[] filterX(float[] vertices) {
        return filterX(vertices, tmpFloatArr);
    }

    /** @param vertices the vertices in [x, y, z, x, y, z, ...] order
     *  @see #filterX(float[], float[]) */
    public static float[] filterX3D(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, -2, 3, output);
    }

    /** @see #filterX3D(float[], float[]) */
    public static float[] filterX3D(float[] vertices) {
        return filterX3D(vertices, tmpFloatArr);
    }

    /** @return the y values of the given vertices */
    public static float[] filterY(Vector2[] vertices, float[] output) {
        if(output == null || output.length != vertices.length)
            output = new float[vertices.length];
        for(int i = 0; i < output.length; i++)
            output[i] = vertices[i].y;
        return output;
    }

    /** @see #filterY(com.badlogic.gdx.math.Vector2[], float[]) */
    public static float[] filterY(Vector2[] vertices) {
        return filterY(vertices, tmpFloatArr);
    }

    /** @see #filterY(com.badlogic.gdx.math.Vector2[], float[])
     *  @see #filterX(float[], float[])*/
    public static float[] filterY(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, 2, output);
    }

    /** @see #filterY(float[], float[]) */
    public static float[] filterY(float[] vertices) {
        return filterY(vertices, tmpFloatArr);
    }

    /** @see #filterY(float[], float[])
     *  @see #filterX3D(float[], float[]) */
    public static float[] filterY3D(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, -4, 3, output);
    }

    /** @see #filterY3D(float[], float[]) */
    public static float[] filterY3D(float[] vertices) {
        return filterY3D(vertices, tmpFloatArr);
    }

    /** @see #filterX(com.badlogic.gdx.math.Vector2[], float[])
     *  @see #filterX3D(float[], float[]) */
    public static float[] filterZ(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, 3, output);
    }

    /** @see #filterZ(float[], float[]) */
    public static float[] filterZ(float[] vertices) {
        return filterZ(vertices, tmpFloatArr);
    }

    /** @see #filterX3D(float[]) */
    public static float[] filterW(float[] vertices, float[] output) {
        return ArrayUtils.select(vertices, 4, output);
    }

    /** @see #filterW(float[], float[]) */
    public static float[] filterW(float[] vertices) {
        return filterW(vertices, tmpFloatArr);
    }

    /** @return the min x value of the given vertices */
    public static float minX(Vector2[] vertices) {
        return min(filterX(vertices));
    }

    /** @return the min y value of the given vertices */
    public static float minY(Vector2[] vertices) {
        return min(filterY(vertices));
    }

    /** @return the max x value of the given vertices */
    public static float maxX(Vector2[] vertices) {
        return max(filterX(vertices));
    }

    /** @return the max y value of the given vertices */
    public static float maxY(Vector2[] vertices) {
        return max(filterY(vertices));
    }

    /** @see #minX(com.badlogic.gdx.math.Vector2[]) */
    public static float minX(float[] vertices) {
        return min(filterX(vertices));
    }

    /** @see #minY(com.badlogic.gdx.math.Vector2[]) */
    public static float minY(float[] vertices) {
        return min(filterY(vertices));
    }

    /** @see #maxX(com.badlogic.gdx.math.Vector2[]) */
    public static float maxX(float[] vertices) {
        return max(filterX(vertices));
    }

    /** @see #maxY(com.badlogic.gdx.math.Vector2[]) */
    public static float maxY(float[] vertices) {
        return max(filterY(vertices));
    }

    /** rotates a {@code point} around {@code center}
     *  @param point the point to rotate
     *  @param origin the point around which to rotate {@code point}
     *  @param radians the rotation
     *  @return the given {@code point} rotated around {@code center} by {@code radians} */
    public static Vector2 rotate(Vector2 point, Vector2 origin, float radians) {
        if(point.equals(origin))
            return point;
        return point.add(origin).rotateRad(radians).sub(origin);
    }

    /** rotates the line around its center (same as {@link #rotate(com.badlogic.gdx.math.Vector2, com.badlogic.gdx.math.Vector2, float)} using the center between both points as origin)
     *  @param a a point on the line
     *  @param b another point on the line
     *  @param radians the rotation */
    public static void rotateLine(Vector2 a, Vector2 b, float radians) {
        rotate(a, vec2_0.set(a).add(b).scl(.5f), radians);
        rotate(b, vec2_0, radians);
    }

    /** returns the vertices of a rotated rectangle
     *  @param x the x of the rectangle
     *  @param y the y of the rectangle
     *  @param width the width of the rectangle
     *  @param height the height of the rectangle
     *  @param radians the angle to rotate the rectangle by
     *  @param output The array to store the results in. Will be recreated if it is null or its length is not 8.
     *  @return the rotated vertices */
    public static float[] rotate(float x, float y, float width, float height, float radians, float[] output) {
        // http://www.monkeycoder.co.nz/Community/posts.php?topic=3935
        float rad = (float) (Math.sqrt(height * height + width * width) / 2.);
        float theta = MathUtils.atan2(height, width);
        float x0 = (float) (rad * Math.cos(theta + radians));
        float y0 = (float) (rad * Math.sin(theta + radians));
        float x1 = (float) (rad * Math.cos(-theta + radians));
        float y1 = (float) (rad * Math.sin(-theta + radians));
        float offsetX = x + width / 2, offsetY = y + height / 2;
        if(output == null || output.length != 8)
            output = new float[8];
        output[0] = offsetX + x0;
        output[1] = offsetY + y0;
        output[2] = offsetX + x1;
        output[3] = offsetY + y1;
        output[4] = offsetX - x0;
        output[5] = offsetY - y0;
        output[6] = offsetX - x1;
        output[7] = offsetY - y1;
        return output;
    }

    /** @see #rotate(float, float, float, float, float, float[]) */
    public static float[] rotate(float x, float y, float width, float height, float radians) {
        return rotate(x, y, width, height, radians, tmpFloatArr);
    }

    /** @see #rotate(float, float, float, float, float, float[]) */
    public static float[] rotate(Rectangle rectangle, float radians, float[] output) {
        return rotate(rectangle.x, rectangle.y, rectangle.width, rectangle.height, radians, output);
    }

    /** @see #rotate(com.badlogic.gdx.math.Rectangle, float, float[]) */
    public static float[] rotate(Rectangle rectangle, float radians) {
        return rotate(rectangle, radians, tmpFloatArr);
    }

    /** @param vector2s the Vector2[] to convert to a float[]
     *  @return the float[] converted from the given Vector2[] */
    public static float[] toFloatArray(Vector2[] vector2s, float[] output) {
        if(output == null || output.length != vector2s.length * 2)
            output = new float[vector2s.length * 2];

        for(int i = 0, vi = -1; i < output.length; i++)
            if(i % 2 == 0)
                output[i] = vector2s[++vi].x;
            else
                output[i] = vector2s[vi].y;

        return output;
    }

    /** @see #toFloatArray(com.badlogic.gdx.math.Vector2[], float[]) */
    public static float[] toFloatArray(Vector2[] vector2s) {
        return toFloatArray(vector2s, tmpFloatArr);
    }

    /** @param floats the float[] to convert to a Vector2[]
     *  @return the Vector2[] converted from the given float[] */
    public static Vector2[] toVector2Array(float[] floats, Vector2[] output) {
        if(floats.length % 2 != 0)
            throw new IllegalArgumentException("the float array's length is not dividable by two, so it won't make up a Vector2 array: " + floats.length);

        if(output == null || output.length != floats.length / 2) {
            output = new Vector2[floats.length / 2];
            for(int i = 0; i < output.length; i++)
                output[i] = new Vector2();
        }

        for(int i = 0, fi = -1; i < output.length; i++)
            output[i].set(floats[++fi], floats[++fi]);

        return output;
    }

    /** @see #toVector2Array(float[], com.badlogic.gdx.math.Vector2[]) */
    public static Vector2[] toVector2Array(float[] floats) {
        return toVector2Array(floats, tmpVecArr);
    }

    /** @param vertexCount the number of vertices for each {@link com.badlogic.gdx.math.Polygon}
     *  @see #toPolygonArray(com.badlogic.gdx.math.Vector2[], int[]) */
    public static Polygon[] toPolygonArray(Vector2[] vertices, int vertexCount) {
        int[] vertexCounts = new int[vertices.length / vertexCount];
        for(int i = 0; i < vertexCounts.length; i++)
            vertexCounts[i] = vertexCount;
        return toPolygonArray(vertices, vertexCounts);
    }

    /** @param vertices the vertices which should be split into a {@link com.badlogic.gdx.math.Polygon} array
     *  @param vertexCounts the number of vertices of each {@link com.badlogic.gdx.math.Polygon}
     *  @return the {@link com.badlogic.gdx.math.Polygon} array extracted from the vertices */
    public static Polygon[] toPolygonArray(Vector2[] vertices, int[] vertexCounts) {
        Polygon[] polygons = new Polygon[vertexCounts.length];

        for(int i = 0, vertice = -1; i < polygons.length; i++) {
            tmpVecArr = new Vector2[vertexCounts[i]];
            for(int i2 = 0; i2 < tmpVecArr.length; i2++)
                tmpVecArr[i2] = vertices[++vertice];
            polygons[i] = new Polygon(toFloatArray(tmpVecArr));
        }

        return polygons;
    }

    /** @param polygon the polygon, assumed to be simple
     *  @return if the vertices are in clockwise order */
    public static boolean areVerticesClockwise(Polygon polygon) {
        return polygon.area() < 0;
    }

    /** @see #areVerticesClockwise(com.badlogic.gdx.math.Polygon) */
    public static boolean areVerticesClockwise(float[] vertices) {
        if(vertices.length <= 4)
            return true;
        return area(vertices) < 0;
    }

    /** used in {@link #arrangeClockwise(com.badlogic.gdx.utils.Array)} */
    private static final Comparator<Vector2> arrangeClockwiseComparator = new Comparator<Vector2>() {

        /** compares the x coordinates */
        @Override
        public int compare(Vector2 a, Vector2 b) {
            if(a.x > b.x)
                return 1;
            else if(a.x < b.x)
                return -1;
            return 0;
        }

    };

    /** @param vertices the vertices to arrange in clockwise order */
    public static void arrangeClockwise(Array<Vector2> vertices) {
        // http://www.emanueleferonato.com/2011/08/05/slicing-splitting-and-cutting-objects-with-box2d-part-4-using-real-graphics
        int n = vertices.size, i1 = 1, i2 = vertices.size - 1;

        if(tmpVecArr == null || tmpVecArr.length < n)
            tmpVecArr = new Vector2[vertices.size];
        System.arraycopy(vertices.items, 0, tmpVecArr, 0, n);
        Arrays.sort(tmpVecArr, arrangeClockwiseComparator);

        tmpVecArr[0] = vertices.get(0);
        Vector2 C = vertices.get(0);
        Vector2 D = vertices.get(n - 1);

        float det;
        for(int i = 1; i < n - 1; i++) {
            det = det(C.x, C.y, D.x, D.y, vertices.get(i).x, vertices.get(i).y);
            if(det < 0)
                tmpVecArr[i1++] = vertices.get(i);
            else
                tmpVecArr[i2--] = vertices.get(i);
        }

        tmpVecArr[i1] = vertices.get(n - 1);

        vertices.clear();
        vertices.addAll(tmpVecArr, 0, n);
    }

    /** @see #isConvex(com.badlogic.gdx.math.Vector2[]) */
    public static boolean isConvex(float[] vertices) {
        return isConvex(toVector2Array(vertices));
    }

    /** @see #isConvex(com.badlogic.gdx.math.Vector2[]) */
    public static boolean isConvex(Polygon polygon) {
        return isConvex(polygon.getVertices());
    }

    /** @return the area of the polygon */
    public static float area(float[] vertices) {
        // from com.badlogic.gdx.math.Polygon#area()
        float area = 0;

        int x1, y1, x2, y2;
        for(int i = 0; i < vertices.length; i += 2) {
            x1 = i;
            y1 = i + 1;
            x2 = (i + 2) % vertices.length;
            y2 = (i + 3) % vertices.length;

            area += vertices[x1] * vertices[y2];
            area -= vertices[x2] * vertices[y1];
        }

        return area /= 2;
    }

    /** @param vertices the vertices of the polygon to examine for convexity
     *  @return if the polygon is convex */
    public static boolean isConvex(Vector2[] vertices) {
        // http://www.sunshine2k.de/coding/java/Polygon/Convex/polygon.htm
        Vector2 p, v = vec2_1, u;
        float res = 0;
        for(int i = 0; i < vertices.length; i++) {
            p = vertices[i];
            vec2_0.set(vertices[(i + 1) % vertices.length]);
            v.x = vec2_0.x - p.x;
            v.y = vec2_0.y - p.y;
            u = vertices[(i + 2) % vertices.length];

            if(i == 0) // in first loop direction is unknown, so save it in res
                res = u.x * v.y - u.y * v.x + v.x * p.y - v.y * p.x;
            else {
                float newres = u.x * v.y - u.y * v.x + v.x * p.y - v.y * p.x;
                if(newres > 0 && res < 0 || newres < 0 && res > 0)
                    return false;
            }
        }

        return true;
    }

    /** @param concave the concave polygon to triangulate
     *  @return an array of triangles representing the given concave polygon
     *  @see com.badlogic.gdx.math.EarClippingTriangulator#computeTriangles(float[]) */
    public static Polygon[] triangulate(Polygon concave) {
        Vector2[] polygonVertices = toVector2Array(concave.getTransformedVertices());
        ShortArray indices = new EarClippingTriangulator().computeTriangles(toFloatArray(polygonVertices));
        Vector2[] vertices = new Vector2[indices.size];
        for(int i = 0; i < indices.size; i++)
            vertices[i] = polygonVertices[indices.get(i)];
        return toPolygonArray(vertices, 3);
    }

    /** @param concave the concave polygon to to decompose
     *  @return an array of convex polygons representing the given concave polygon
     *  @see BayazitDecomposer#convexPartition(com.badlogic.gdx.utils.Array) */
    public static Polygon[] decompose(Polygon concave) {
        Array<Array<Vector2>> convexPolys = BayazitDecomposer.convexPartition(new Array<Vector2>(toVector2Array(concave.getTransformedVertices())));
        Polygon[] convexPolygons = new Polygon[convexPolys.size];
        for(int i = 0; i < convexPolygons.length; i++)
            convexPolygons[i] = new Polygon(toFloatArray((Vector2[]) convexPolys.get(i).toArray(Vector2.class)));
        return convexPolygons;
    }

    /** Keeps the first described rectangle in the second described rectangle. If the second rectangle is smaller than the first one, the first will be centered on the second one.
     *  @param position the position of the first rectangle
     *  @param width the width of the first rectangle
     *  @param height the height of the first rectangle
     *  @param x2 the x of the second rectangle
     *  @param y2 the y of the second rectangle
     *  @param width2 the width of the second rectangle
     *  @param height2 the height of the second rectangle
     *  @return the position of the first rectangle */
    public static Vector2 keepWithin(Vector2 position, float width, float height, float x2, float y2, float width2, float height2) {
        if(width2 < width)
            position.x = x2 + width2 / 2 - width / 2;
        else if(position.x < x2)
            position.x = x2;
        else if(position.x + width > x2 + width2)
            position.x = x2 + width2 - width;
        if(height2 < height)
            position.y = y2 + height2 / 2 - height / 2;
        else if(position.y < y2)
            position.y = y2;
        else if(position.y + height > y2 + height2)
            position.y = y2 + height2 - height;
        return position;
    }

    /** @see #keepWithin(com.badlogic.gdx.math.Vector2, float, float, float, float, float, float) */
    public static Vector2 keepWithin(float x, float y, float width, float height, float rectX, float rectY, float rectWidth, float rectHeight) {
        return keepWithin(vec2_0.set(x, y), width, height, rectX, rectY, rectWidth, rectHeight);
    }

    /** @see #keepWithin(float, float, float, float, float, float, float, float) */
    public static Rectangle keepWithin(Rectangle rect, Rectangle other) {
        return rect.setPosition(keepWithin(rect.x, rect.y, rect.width, rect.height, other.x, other.y, other.width, other.height));
    }

    /** Keeps the given {@link com.badlogic.gdx.graphics.OrthographicCamera} in the given rectangle. If the rectangle is smaller than the camera viewport times the camera zoom, the camera will be centered on the rectangle.<br>
     *  Note that the camera will not be {@link com.badlogic.gdx.graphics.OrthographicCamera#update() updated}.
     *  @param camera the camera to keep in the rectangle
     *  @see #keepWithin(float, float, float, float, float, float, float, float) */
    public static void keepWithin(OrthographicCamera camera, float x, float y, float width, float height) {
        vec2_0.set(keepWithin(camera.position.x - camera.viewportWidth / 2 * camera.zoom, camera.position.y - camera.viewportHeight / 2 * camera.zoom, camera.viewportWidth * camera.zoom, camera.viewportHeight * camera.zoom, x, y, width, height));
        camera.position.x = vec2_0.x + camera.viewportWidth / 2 * camera.zoom;
        camera.position.y = vec2_0.y + camera.viewportHeight / 2 * camera.zoom;
    }

    /** @param a the first point of the segment
     *  @param b the second point of the segment
     *  @param polygon the polygon, assumed to be convex
     *  @param intersection1 the first intersection point
     *  @param intersection2 the second intersection point
     *  @return the number of intersection points
     *  @see #intersectSegments(com.badlogic.gdx.math.Vector2, com.badlogic.gdx.math.Vector2, float[], boolean, com.badlogic.gdx.utils.Array)*/
    public static int intersectSegments(Vector2 a, Vector2 b, float[] polygon, Vector2 intersection1, Vector2 intersection2) {
        FloatArray intersections = Pools.obtain(FloatArray.class);
        intersectSegments(a.x, a.y, b.x, b.y, polygon, true, intersections);
        int size = intersections.size;
        if(size >= 2) {
            intersection1.set(intersections.get(0), intersections.get(1));
            if(size == 4)
                intersection2.set(intersections.get(2), intersections.get(3));
            else if(size > 4)
                assert false : "more intersection points with a convex polygon found than possible: " + size;
        }
        Pools.free(intersections);
        return size / 2;
    }

    /** @see #intersectSegments(float, float, float, float, float[], boolean, com.badlogic.gdx.utils.FloatArray) */
    public static void intersectSegments(Vector2 a, Vector2 b, float[] segments, boolean polygon, Array<Vector2> intersections) {
        FloatArray fa = Pools.obtain(FloatArray.class);
        intersectSegments(a.x, a.y, b.x, b.y, segments, polygon, fa);
        if(fa.size < 1) {
            intersections.clear();
            Pools.free(fa);
            return;
        }
        intersections.ensureCapacity(fa.size / 2 - intersections.size);
        for(int i = 1; i < fa.size; i += 2)
            if(intersections.size > i / 2)
                intersections.get(i / 2).set(fa.get(i - 1), fa.get(i));
            else
                intersections.add(new Vector2(fa.get(i - 1), fa.get(i)));
        Pools.free(fa);
    }

    /** @param segments the segments
     *  @param polygon if the segments represent a closed polygon
     *  @param intersections the array to store the intersections in */
    public static void intersectSegments(float x1, float y1, float x2, float y2, float[] segments, boolean polygon, FloatArray intersections) {
        if(polygon && segments.length < 6)
            throw new IllegalArgumentException("a polygon consists of at least 3 points: " + segments.length);
        else if(segments.length < 4)
            throw new IllegalArgumentException("segments does not contain enough vertices to represent at least one segment: " + segments.length);
        if(segments.length % 2 != 0)
            throw new IllegalArgumentException("malformed segments; the number of vertices is not dividable by 2: " + segments.length);
        intersections.clear();
        Vector2 tmp = Pools.obtain(Vector2.class);
        for(int i = 0, n = segments.length - (polygon ? 0 : 2); i < n; i += 2) {
            float x3 = segments[i], y3 = segments[i + 1], x4 = wrapIndex(i + 2, segments), y4 = wrapIndex(i + 3, segments);
            if(Intersector.intersectSegments(x1, y1, x2, y2, x3, y3, x4, y4, tmp)) {
                intersections.add(tmp.x);
                intersections.add(tmp.y);
            }
        }
        Pools.free(tmp);
    }

}