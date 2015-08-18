package org.bimserver.cobie.graphics;
//package org.bimserver.cobie.graphics;

//import org.bimserver.emf.IfcModelInterface;
//import org.bimserver.models.ifc2x3tc1.IfcArbitraryClosedProfileDef;
//import org.bimserver.models.ifc2x3tc1.IfcAxis2Placement3D;
//import org.bimserver.models.ifc2x3tc1.IfcCartesianPoint;
//import org.bimserver.models.ifc2x3tc1.IfcExtrudedAreaSolid;
//import org.bimserver.models.ifc2x3tc1.IfcLocalPlacement;
//import org.bimserver.models.ifc2x3tc1.IfcObjectPlacement;
//import org.bimserver.models.ifc2x3tc1.IfcPolyline;
//import org.bimserver.models.ifc2x3tc1.IfcProductDefinitionShape;
//import org.bimserver.models.ifc2x3tc1.IfcProductRepresentation;
//import org.bimserver.models.ifc2x3tc1.IfcProfileDef;
//import org.bimserver.models.ifc2x3tc1.IfcProfileTypeEnum;
//import org.bimserver.models.ifc2x3tc1.IfcRepresentation;
//import org.bimserver.models.ifc2x3tc1.IfcRepresentationItem;
//import org.bimserver.models.ifc2x3tc1.IfcShapeRepresentation;
//import org.bimserver.models.ifc2x3tc1.IfcSpace;

// This is here as a starting point if we have to get the space geometry out of the IFC model directly.
//public class VertexExtractor
//{
//    public static void doStuff(IfcModelInterface model)
//    {
//        for(IfcSpace space : model.getAll(IfcSpace.class))
//        {
//            IfcObjectPlacement placement = space.getObjectPlacement();
//            if(placement instanceof IfcLocalPlacement)
//            {
//                IfcLocalPlacement localPlacement = (IfcLocalPlacement) placement;
//                IfcProductRepresentation representation = space.getRepresentation();
//                if(representation instanceof IfcProductDefinitionShape)
//                {
//                    IfcProductDefinitionShape productDefinitionShape =
//                            (IfcProductDefinitionShape) representation;
//                    doStuffWithPlacementAndShape(localPlacement, productDefinitionShape);
//                }
//            }
//        }
//    }
//
//    private static void doStuffWithPlacementAndShape(IfcLocalPlacement localPlacement, IfcProductDefinitionShape productDefinitionShape)
//    {
//        for (IfcRepresentation representation : productDefinitionShape.getRepresentations())
//        {
//            if(representation instanceof IfcShapeRepresentation)
//            {
//                IfcShapeRepresentation shapeRepresentation = (IfcShapeRepresentation) representation;
//                for(IfcRepresentationItem representationItem : shapeRepresentation.getItems())
//                {
//                    if(representationItem instanceof IfcExtrudedAreaSolid)
//                    {
//                        IfcExtrudedAreaSolid extrudedAreaSolid =
//                                (IfcExtrudedAreaSolid) representationItem;
//                        doStuffWithExtrudedAreaSolid(localPlacement, extrudedAreaSolid);
//                    }
//                }
//            }
//        }
//        
//    }
//
//    private static void doStuffWithExtrudedAreaSolid(IfcLocalPlacement localPlacement, IfcExtrudedAreaSolid extrudedAreaSolid)
//    {
//        IfcAxis2Placement3D placement = extrudedAreaSolid.getPosition();
//        IfcProfileDef profile = extrudedAreaSolid.getSweptArea();        
//        doStuffWithProfileObjectPlacementAndLocalPlacement(profile, placement, localPlacement);
//    }
//
//    private static void doStuffWithProfileObjectPlacementAndLocalPlacement(
//            IfcProfileDef profile,
//            IfcAxis2Placement3D placement,
//            IfcLocalPlacement localPlacement)
//    {
//        if(profile instanceof IfcArbitraryClosedProfileDef)
//        {
//            handleArbitraryClosedProfileDef((IfcArbitraryClosedProfileDef) profile, placement, localPlacement);
//        }
//        
//    }
//
//    private static void handleArbitraryClosedProfileDef(
//            IfcArbitraryClosedProfileDef profile,
//            IfcAxis2Placement3D placement,
//            IfcLocalPlacement localPlacement)
//    {
//        if (profile.getProfileType() == IfcProfileTypeEnum.AREA && profile.getOuterCurve() instanceof IfcPolyline)
//        {
//            IfcPolyline polyLine =
//                    (IfcPolyline) profile.getOuterCurve();
//            for(IfcCartesianPoint cartesianPoint : polyLine.getPoints())
//            {
//                
//            }
//        }
//    }   
//}
