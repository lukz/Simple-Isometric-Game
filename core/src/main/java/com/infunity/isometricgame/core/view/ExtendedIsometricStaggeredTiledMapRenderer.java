package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.core.view.entityRenderers.PlayerRenderer;
import com.infunity.isometricgame.shared.model.entities.Player;

import static com.badlogic.gdx.graphics.g2d.Batch.*;

/**
 *  Isometric staggered tiled map renderer with ability to render players on map in correct order
 */
public class ExtendedIsometricStaggeredTiledMapRenderer extends IsometricStaggeredTiledMapRenderer {

    public ExtendedIsometricStaggeredTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer, PlayerRenderer playerRenderer, Array<Player> playersToRender) {

        final Color batchColor = spriteBatch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        final float layerTileWidth50 = layerTileWidth * 0.50f;
        final float layerTileHeight50 = layerTileHeight * 0.50f;

        final int minX = Math.max(0, (int)(((viewBounds.x - layerTileWidth50) / layerTileWidth)));
        final int maxX = Math.min(layerWidth,
                (int)((viewBounds.x + viewBounds.width + layerTileWidth + layerTileWidth50) / layerTileWidth));

        final int minY = Math.max(0, (int)(((viewBounds.y - layerTileHeight) / layerTileHeight)));
        final int maxY = Math.min(layerHeight, (int)((viewBounds.y + viewBounds.height + layerTileHeight) / layerTileHeight50));

        for (int y = maxY - 1; y >= minY; y--) {
            float offsetX = (y % 2 == 1) ? layerTileWidth50 : 0;
            for (int x = maxX - 1; x >= minX; x--) {
                final TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) continue;
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();
                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x * layerTileWidth - offsetX + tile.getOffsetX() * unitScale;
                    float y1 = y * layerTileHeight50 + tile.getOffsetY() * unitScale;
                    float x2 = x1 + region.getRegionWidth() * unitScale;
                    float y2 = y1 + region.getRegionHeight() * unitScale;

                    float u1 = region.getU();
                    float v1 = region.getV2();
                    float u2 = region.getU2();
                    float v2 = region.getV();

                    vertices[X1] = x1;
                    vertices[Y1] = y1;
                    vertices[C1] = color;
                    vertices[U1] = u1;
                    vertices[V1] = v1;

                    vertices[X2] = x1;
                    vertices[Y2] = y2;
                    vertices[C2] = color;
                    vertices[U2] = u1;
                    vertices[V2] = v2;

                    vertices[X3] = x2;
                    vertices[Y3] = y2;
                    vertices[C3] = color;
                    vertices[U3] = u2;
                    vertices[V3] = v2;

                    vertices[X4] = x2;
                    vertices[Y4] = y1;
                    vertices[C4] = color;
                    vertices[U4] = u2;
                    vertices[V4] = v1;

                    if (flipX) {
                        float temp = vertices[U1];
                        vertices[U1] = vertices[U3];
                        vertices[U3] = temp;
                        temp = vertices[U2];
                        vertices[U2] = vertices[U4];
                        vertices[U4] = temp;
                    }

                    if (flipY) {
                        float temp = vertices[V1];
                        vertices[V1] = vertices[V3];
                        vertices[V3] = temp;
                        temp = vertices[V2];
                        vertices[V2] = vertices[V4];
                        vertices[V4] = temp;
                    }

                    if (rotations != 0) {
                        switch (rotations) {
                            case TiledMapTileLayer.Cell.ROTATE_90: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V2];
                                vertices[V2] = vertices[V3];
                                vertices[V3] = vertices[V4];
                                vertices[V4] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U2];
                                vertices[U2] = vertices[U3];
                                vertices[U3] = vertices[U4];
                                vertices[U4] = tempU;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_180: {
                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U3];
                                vertices[U3] = tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V3];
                                vertices[V3] = tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case TiledMapTileLayer.Cell.ROTATE_270: {
                                float tempV = vertices[V1];
                                vertices[V1] = vertices[V4];
                                vertices[V4] = vertices[V3];
                                vertices[V3] = vertices[V2];
                                vertices[V2] = tempV;

                                float tempU = vertices[U1];
                                vertices[U1] = vertices[U4];
                                vertices[U4] = vertices[U3];
                                vertices[U3] = vertices[U2];
                                vertices[U2] = tempU;
                                break;
                            }
                        }
                    }

                    // Render player if before tile
                    if(playersToRender.size > 0) {
                        Player player = playersToRender.peek();
                        if(player.getPositionY() - player.getSpriteBoundingHeight() / 2 > vertices[1]) {
                            playerRenderer.render((SpriteBatch)spriteBatch, player);
                            playersToRender.removeIndex(playersToRender.size - 1);
                        }
                    }

                    spriteBatch.draw(region.getTexture(), vertices, 0, 20);
                }
            }
        }
    }
}
