floorplan-generator
===================

August 18, 2015

This is a Serializer Plug-in for BIMserver that creates a 2D HTML floorplan with embedded COBie metadata.  The initial version of this code was developed by the US Army Corps of Engineers,
Engineer Research Development Center in cooperation with the National Institute of Building Sciences. 

This project depends on some of the resources included in the standard COBie plugins page (the COBieShared project).  
Future revisions will eliminate the COBieShared dependency, but for now, you must import the COBieShared project (of the COBiePlugins repository)if you want to
work with the source code.

j3dcore.jar, j3dutils.jar, and vecmath.jar must be in the Bimserver/lib directory.  See Java3d/J3d.

The j3d dependencies need to be setup to point to the native dll (on windows machines), j3dcore-ogl.dll.  If you are running this in Eclipse, you could put the native
dll in  BimServer/native/j3dcore-ogl.dll and reference that in your classpath setup.  

If this plugin works correctly then it will export a .zip file containing static html pages, javascript files, etc.  For best results, publish these files on a Web
Server and access them via http.  There is even a lightweight Web Server app for Chrome that works well for local viewing - see "Web Server for Chrome" in the 
Chrome App store.  Otherwise, you must disable some important browser security settings to properly view the generated pages via a file:/// link.

There are also some inconsistent behavior accross browsers, but this will be addressed soon.  

Build scripts and other information to come...




