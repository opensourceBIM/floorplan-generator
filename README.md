floorplan-generator
===================

Plug-in for BIMserver to create a 2D HTML floorplan generator.

This project depends on some of the resources included in the standard COBie plugins page (the COBieShared project).  
Future revisions will eliminate the COBieShared dependency, but for now, you must import the COBieShared project (of the COBiePlugins repository)if you want to
work with the source code.

j3dcore.jar, j3dutils.jar, and vecmath.jar must be in the Bimserver/lib directory.  See Java3d/J3d.

The j3d dependencies need to be setup to point to the native dll (on windows machines). j3dcore-ogl.dll.  We have been putting that dll
in BimServer/native/j3dcore-ogl.dll.

For now, you must install J3D locally to use the floorplan generator:
https://java3d.java.net/binary-builds.html

For windows users, download the latest relevant (x86 or x64) release under the Installers heading.




