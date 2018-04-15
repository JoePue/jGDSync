package de.puettner.jgdsync.gdservice.console;

import de.puettner.jgdsync.model.GDFile;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;

import java.util.List;

import static java.lang.System.out;

public class ConsolePrinter {

    public ConsolePrinter() {
    }

    public void printFileList(Node<SyncNode> nodeList) {
        String intendFolder = "| ";
        String intendFile = "|- ";

        nodeList.getChildren().stream().filter(file -> !file.isLeaf()).forEach(file -> {
            printFolder(file, intendFolder);
        });
        nodeList.getChildren().stream().filter(file -> file.isLeaf()).forEach(file -> {
            printFile(file, intendFile);
        });
    }

    private void printFolder(Node<SyncNode> nodeList, String intend) {
        printFile(nodeList, intend.substring(0, intend.length() - 2) + "+ ");
        String intendFolder = intend + "| ";
        String intendFile = intend + "|- ";

        nodeList.getChildren().stream().filter(file -> !file.isLeaf()).forEach(file -> {
            printFolder(file, intendFolder);
        });
        nodeList.getChildren().stream().filter(file -> file.isLeaf()).forEach(file -> {
            printFile(file, intendFile);
        });
    }

    public void printFile(Node<SyncNode> node, String intend) {
        GDFile file = node.getData().getGdFile();
        out.printf("%-50s%-35s %-60s %-40s", (trim(intend + file.getName(), 50)), file.getModifiedTime(), file.getId(), file.getMimeType
                (), file.getParents());
        List<String> parents = file.getParents();
        if (parents != null && parents.size() > 0) {
            out.printf("\t\tparents: ");
            for (String parent : parents) {
                out.printf("%s ", parent);
            }
        }
        out.println();
    }

    private String trim(String name, int size) {
        if (name.length() > size) {
            return name.substring(0, size - 3) + "..";
        } else {
            return name;
        }
    }
}
