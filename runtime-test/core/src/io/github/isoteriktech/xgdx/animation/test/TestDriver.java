package io.github.isoteriktech.xgdx.animation.test;

import com.badlogic.gdx.graphics.Texture;
import com.isoterik.racken.GameDriver;
import com.isoterik.racken.Scene;

public class TestDriver extends GameDriver {
	@Override
	protected Scene initGame() {
		racken.assets.enqueueFolderContents("sprites", Texture.class);
		racken.assets.loadAssetsNow();

		return new AnimatorTest();
	}
}