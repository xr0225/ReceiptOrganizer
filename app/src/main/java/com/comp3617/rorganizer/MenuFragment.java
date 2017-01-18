/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.comp3617.rorganizer;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class MenuFragment extends BaseFragment {

	protected static AbsListView listView;
    protected static String[] IMAGE_URLS;
    protected static String[] IMAGE_URL;
	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;



	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuItem pauseOnScrollItem = menu.findItem(R.id.item_pause_on_scroll);
		pauseOnScrollItem.setVisible(true);
		pauseOnScrollItem.setChecked(pauseOnScroll);

		MenuItem pauseOnFlingItem = menu.findItem(R.id.item_pause_on_fling);
		pauseOnFlingItem.setVisible(true);
		pauseOnFlingItem.setChecked(pauseOnFling);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_pause_on_scroll:
				pauseOnScroll = !pauseOnScroll;
				item.setChecked(pauseOnScroll);
				applyScrollListener();
				return true;
			case R.id.item_pause_on_fling:
				pauseOnFling = !pauseOnFling;
				item.setChecked(pauseOnFling);
				applyScrollListener();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected void startImagePagerActivity(int position) {
        Intent intent = new Intent(getActivity(), PagerFrag.class);
        intent.putExtra("com.comp3617.rorgnizer.IMAGE_POSITION", position);
        startActivity(intent);
	}

	private void applyScrollListener() {
		listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
	}
}
