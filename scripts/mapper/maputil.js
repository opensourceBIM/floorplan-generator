/**
 * maputil.js 1.4 (29-Nov-2010)
 * (c) by Christian Effenberger 
 * All Rights Reserved
 * Source: mapper.netzgesta.de
 * Distributed under Netzgestade Software License Agreement
 * http://www.netzgesta.de/cvi/LICENSE.txt
 * License permits free of charge
 * use on non-commercial and 
 * private web sites only 

usage: makeSVGfromMap(map, options);
	map: should be a map element: document.getElementsByName("myimg")[0]
	options: (contains zero or more values)
	arg1 = output e.g. true (return svg document) | false (open window with source)
	arg2 = width e.g. 502 (image width as pixel value)
	arg3 = height e.g. 482 (image height as pixel value)
	arg4 = strokewidth e.g. 0.75 | default is 0.5 pixel
	arg5 = stroke-miterlimit e.g. 8 | default is 1
	arg6 = initialfillcolor e.g. #cccccc | default is silver
	arg7 = initialstrokecolor e.g. #808080 | default is gray
	arg8 = hoverfillcolor e.g. #66ff66 | default is limegreen
	arg9 = hoverstrokecolor e.g. #00ff00 | default is green
	arg10= backgroundcolor e.g. #ffffff | default is null
	
usage: scaleMapAreaCoords(map, nw, nh, sw, sh, options);
	map: should be a map element: document.getElementsByName("myimg")[0]
	nw = width e.g. 800 (natural image width as pixel value)
	nh = height e.g. 400 (natural image height as pixel value)
	sw = width e.g. 400 (scaled image width as pixel value)
	sh = height e.g. 200 (scaled image height as pixel value)
	options: (contains zero or more values)
	arg1 = xoff e.g. -2 (horizontal offset as pixel value)
	arg2 = yoff e.g. -2 (vertical offset as pixel value)
**/

function makeSVGfromMap(obj,out,w,h,stw,sml,ifc,isc,hfc,hsc,bgc) {
	function getCl(cla,str,rep){var j,ar,pd,tt=rep; pd=(cla.indexOf(';')!=-1?cla.split(';'):pd=cla); for(j=0;j<pd.length;j++) {if(pd[j].indexOf(':')!=-1) {ar=pd[j].split(':'); if(ar[0].indexOf(str)!=-1) {tt=ar[1]; break;}}} return tt; }
	if(obj.tagName.toLowerCase()=="map") {
		var a,b,c,d,e,i,j,k,l,n,p,r,s,t,x,y,z,tmp,win,ele=obj.getElementsByTagName("area"); b=stw||0.5; c=sml||1; x=out?"<":"&lt;"; y=out?">":"&gt;"; z=out?"\n":"<br>";
		tmp =x+'?xml version="1.0" encoding="utf-8" standalone="no"?'+y+z;
		tmp+=x+'!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 20010904//EN" "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"'+y+z;
		tmp+=x+'svg width="'+(w||100)+'px" height="'+(h||100)+'px" viewBox="0 0 '+(w||100)+' '+(h||100)+'" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"'+y+z;
		tmp+=x+'title'+y+(obj.id||obj.name||"Unnamed")+x+'/title'+y+x+'desc'+y+'Generated with maputil.js (mapper.netzgesta.de)'+x+'/desc'+y+z;
		tmp+=x+'defs'+y+z+x+'script type="text/ecmascript"'+y+z+x+'![CDATA['+z+'var fdat="",sdat="";'+z;
		tmp+='function onAreaOver(evt){var ele=evt.target; fdat=ele.getAttribute("fill"); sdat=ele.getAttribute("stroke"); ele.setAttribute("fill","'+(hfc||"limegreen")+'"); ele.setAttribute("stroke","'+(hsc||"green")+'");}'+z;
		tmp+='function onAreaOut(evt){var ele=evt.target; ele.setAttribute("fill",fdat); ele.setAttribute("stroke",sdat);}'+z;
		tmp+=']]'+y+z+x+'/script'+y+z+x+'/defs'+y+z;
		if(bgc!='') {tmp+=x+'rect id="background" fill="'+bgc+'" stroke-width="0" x="0" y="0" width="'+(w||100)+'" height="'+(h||100)+'" /'+y+z;}
		for(i=0;i<ele.length;i++) {
			d=ele[i].coords.split(","); s=ele[i].shape; p=''; l=ele[i].title||ele[i].alt; a=ele[i].getAttribute('href'); t=l!=''?l:"area_"+i; e=""; n=ele[i].id||ele[i].name||t; e=ele[i].getAttribute("style");
			if(e!=null&&!window.navigator.systemLanguage) {k=getCl(e,'fill','silver')||ifc||"silver"; r=getCl(e,'stroke','gray')||isc||"gray";}else {k=ifc||"silver"; r=isc||"gray";}
			if(s.toLowerCase()=='rect') {
				tmp+=x+'a xlink:title="'+l+'" xlink:href="'+(a!=''?a:"#")+'"'+y+x+'rect id="'+n+'" fill="'+k+'" stroke="'+r+'" stroke-width="'+b+'px" stroke-miterlimit="'+c+'" onmouseover="onAreaOver(evt);" onmouseout="onAreaOut(evt);" x="'+parseInt(d[0])+'" y="'+parseInt(d[1])+'" width="'+parseInt(d[2]-d[0])+'" height="'+parseInt(d[3]-d[1])+'" /'+y+x+'/a'+y+z;
			}else if(s.toLowerCase()=='circle') {
				tmp+=x+'a xlink:title="'+l+'" xlink:href="'+(a!=''?a:"#")+'"'+y+x+'circle id="'+n+'" fill="'+k+'" stroke="'+r+'" stroke-width="'+b+'px" stroke-miterlimit="'+c+'" onmouseover="onAreaOver(evt);" onmouseout="onAreaOut(evt);" cx="'+parseInt(d[0])+'" cy="'+parseInt(d[1])+'" r="'+parseInt(d[2])+'" /'+y+x+'/a'+y+z;
			}else if(s.toLowerCase()=='poly') {
				for(j=0;j<d.length;j+=2) {p+=parseInt(d[j])+','+parseInt(d[j+1]); if((j+2)<d.length) {p+=',';}} 
				tmp+=x+'a xlink:title="'+l+'" xlink:href="'+(a!=''?a:"#")+'"'+y+x+'polygon id="'+n+'" fill="'+k+'" stroke="'+r+'" stroke-width="'+b+'px" stroke-miterlimit="'+c+'" onmouseover="onAreaOver(evt);" onmouseout="onAreaOut(evt);" points="'+p+'" /'+y+x+'/a'+y+z;
			}
		} tmp+=x+'/svg'+y+z; if(out) {return tmp; }else {win=top.window.open('',"SVG"); win.document.open("text/html"); win.document.write("<pre>"+tmp+"</pre>"); win.document.close(); return false; }
	}
}

function scaleMapAreaCoords(obj,nw,nh,sw,sh,xo,yo) {
	if(obj.tagName.toLowerCase()=="map") {
		var a,d,e,i,j,l,n,p,r,s,t,x,y,w,h,tmp,win,ls="&lt;area ",le=" /&gt;",lf="<br>",ele=obj.getElementsByTagName("area"); 
		w=parseFloat(sw/nw); h=parseFloat(sh/nh); x=parseInt(xo||0); y=parseInt(yo||0); tmp='&lt;map name="'+(obj.name||obj.id)+'"&gt;'+lf;
		for(i=0;i<ele.length;i++) {
			d=ele[i].coords.split(","); p=''; for(j=0;j<d.length;j+=2) {p+=Math.min(Math.max(Math.round(x+(d[j]*w)),0),sw)+','+Math.min(Math.max(Math.round(y+(d[j+1]*h)),0),sh); if((j+2)<d.length) {p+=',';}} 
			s=ele[i].shape||'rect'; t=ele[i].title; a=ele[i].alt; n=ele[i].id; l=ele[i].getAttribute('href')||'#'; e=ele[i].getAttribute('style'); r=ele[i].getAttribute('rel'); tmp+=ls+'shape="'+s+'" '; 
			if(n!=null) {tmp+='id="'+n+'" '}; if(t!=null) {tmp+='title="'+t+'" '}; if(a!=null) {tmp+='alt="'+a+'" '}; if(r!=null) {tmp+='rel="'+r+'" '}; if(e!=null) {tmp+='style="'+e+'" '}; tmp+='href="'+l+'" coords="'+p+'"'+le+lf;
		} tmp+='&lt;/map&gt;'+lf; win=top.window.open('',"COORDS"); win.document.open("text/html"); win.document.write("<pre>"+tmp+"</pre>"); win.document.close(); return false;
	}
}
