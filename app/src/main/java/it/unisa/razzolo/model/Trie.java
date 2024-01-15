package it.unisa.razzolo.model;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import it.unisa.razzolo.MainActivity;

public class Trie
{
    private static class TrieNode
    {
        private final HashMap<Character, TrieNode> children = new HashMap<>();

        private boolean isLeaf;

        public HashMap<Character, TrieNode> getChildren() {
            return children;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf(boolean leaf) {
            isLeaf = leaf;
        }
    }

    private final TrieNode root;
    private static Trie _instance = null;

    private Trie(){
        root = new TrieNode();

        this._build();
    }

    private void _build(){
        BufferedReader reader = null;
        try {
            AssetManager am = MainActivity.getContext().getAssets();
            reader = new BufferedReader(new InputStreamReader(am.open("dictionary.txt")));
            String line;
            while ((line = reader.readLine()) != null)
                this._insert(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Trie getInstance(){
        if (_instance == null)
            _instance = new Trie();

        return _instance;
    }

    private void _insert(String word)
    {
        var children = root.getChildren();
        var node = new TrieNode();

        for (final var c : word.toCharArray())
        {
            if (children.containsKey(c))
                node = children.get(c);
            else
            {
                node = new TrieNode();
                children.put(c, node);
            }

            children = node.getChildren();
        }

        node.setLeaf(true);
    }

    private TrieNode traverse(String str)
    {
        var current = root;

        for (final var c : str.toCharArray())
        {
            if (!current.getChildren().containsKey(c))
                return null;

            current = current.getChildren().get(c);
        }

        return current;
    }

    public boolean search(String word)
    {
        final var node = traverse(word);

        return node != null && node.isLeaf();
    }

    public Set<Character> searchBySubstring(String substring)
    {
        final var node = traverse(substring);

        if (node == null)
            return null;

        return node.getChildren().keySet();
    }
}