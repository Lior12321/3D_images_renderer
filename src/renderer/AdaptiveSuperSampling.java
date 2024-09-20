package renderer;

import java.util.*;

import primitives.Ray;

public class AdaptiveSuperSampling {

	
//	public List<Ray> constructRaysThroughPixel(Camera camera, int x, int y) {
//	    // pixel size in the view plane
//	    double pixelWidth = camera.getWidth() /   camera.getImageWriter().getNx();
//	    double pixelHeight = camera.getHeight() / camera.getImageWriter().getNy();
//
//	    // רשימה לאחסון 4 הקרניים שניצור
//	    List<Ray> rays = new ArrayList<>();
//
//	    // יצירת הקרניים מארבעת הקצוות של הפיקסל
//	    rays.add(camera.constructRay(x - 0.25, y - 0.25)); // שמאל-למעלה
//	    rays.add(camera.constructRay(x + 0.25, y - 0.25)); // ימין-למעלה
//	    rays.add(camera.constructRay(x - 0.25, y + 0.25)); // שמאל-למטה
//	    rays.add(camera.constructRay(x + 0.25, y + 0.25)); // ימין-למטה
//
//	    return rays;
//	}

}
