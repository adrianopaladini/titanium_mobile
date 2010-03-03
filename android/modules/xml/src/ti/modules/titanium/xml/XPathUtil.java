package ti.modules.titanium.xml;

import java.util.ArrayList;
import java.util.List;

import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.TiProxy;
import org.appcelerator.titanium.util.Log;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;

public class XPathUtil {

	private static final String LCAT = "XPath";
	public static class XPathNodeListProxy extends TiProxy
	{
		private List nodeList;
		public XPathNodeListProxy(TiContext context, List nodeList)
		{
			super(context);
			this.nodeList = nodeList;
		}
		
		public int getLength() {
			return nodeList.size();
		}

		public NodeProxy item(int index) {
			Node node = (Node)nodeList.get(index);
			return NodeProxy.getNodeProxy(getTiContext(), node);
		}
	}
	
	public static XPathNodeListProxy evaluate(NodeProxy start, String xpathExpr)
	{
		try {
			XPath xpath = new DOMXPath(xpathExpr);
			List nodes= xpath.selectNodes(start.getNode());
			
			return new XPathNodeListProxy(start.getTiContext(), nodes);
		} catch (JaxenException e) {
			Log.e(LCAT, "Exception selecting nodes in XPath ("+xpathExpr+")", e);
		}
		
		return new XPathNodeListProxy(start.getTiContext(), new ArrayList());
	}
}
