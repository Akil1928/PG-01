package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreePainterTest {

    @Test
    void collectBFS_deberiaRecorrerEnAnchura() {
        RecursionEngine.CallNode root = new RecursionEngine.CallNode("root", 3, 0);
        RecursionEngine.CallNode child1 = new RecursionEngine.CallNode("child1", 2, 1);
        RecursionEngine.CallNode child2 = new RecursionEngine.CallNode("child2", 1, 1);
        RecursionEngine.CallNode grandChild = new RecursionEngine.CallNode("grandChild", 0, 2);

        root.children.add(child1);
        root.children.add(child2);
        child1.children.add(grandChild);

        List<RecursionEngine.CallNode> result = TreePainter.collectBFS(root);

        assertEquals(4, result.size());
        assertEquals("root", result.get(0).label);
        assertEquals("child1", result.get(1).label);
        assertEquals("child2", result.get(2).label);
        assertEquals("grandChild", result.get(3).label);
    }
}