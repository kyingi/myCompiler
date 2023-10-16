package parser.node;

import java.util.ArrayList;

public class CommonNode {
    protected final ArrayList<CommonNode> commonNodes = new ArrayList<>();

    public ArrayList<CommonNode> getCommonNodes() {
        return commonNodes;
    }

    public void addCNodes(CommonNode commonNode){
        commonNodes.add(commonNode);
    }


}
