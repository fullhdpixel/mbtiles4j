package com.myroutes.mbtiles4j;

import com.myroutes.mbtiles4j.*;
import com.myroutes.mbtiles4j.TileIterator;
import com.myroutes.mbtiles4j.model.MetadataEntry;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MBTilesReader {

    private File f;
    private Connection connection;

    public MBTilesReader(File f) throws MBTilesReadException {
        try {
            connection = com.myroutes.mbtiles4j.SQLHelper.establishConnection(f);
        } catch (MBTilesException e) {
            throw new MBTilesReadException("Establish Connection to " + f.getAbsolutePath() + " failed", e);
        }
        this.f = f;
    }

    public File close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
        return f;
    }

    public MetadataEntry getMetadata() throws MBTilesReadException {
        String sql = "SELECT * from metadata;";
        try {
            ResultSet resultSet = com.myroutes.mbtiles4j.SQLHelper.executeQuery(connection, sql);
            MetadataEntry ent = new MetadataEntry();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String value = resultSet.getString("value");
                ent.addKeyValue(name, value);
            }
            return ent;
        } catch (MBTilesException | SQLException e) {
            throw new MBTilesReadException("Get Metadata failed", e);
        }
    }

    public com.myroutes.mbtiles4j.TileIterator getTiles() throws MBTilesReadException {
        String sql = "SELECT * from tiles;";
        try {
            ResultSet resultSet = com.myroutes.mbtiles4j.SQLHelper.executeQuery(connection, sql);
            return new TileIterator(resultSet);
        } catch (MBTilesException e) {
            throw new MBTilesReadException("Access Tiles failed", e);
        }
    }
}
