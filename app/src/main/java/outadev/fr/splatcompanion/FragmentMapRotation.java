package outadev.fr.splatcompanion;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import outadev.fr.splatcompanion.model.GameMode;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.Stage;

/**
 * Created by outadoc on 21/08/15.
 */
public abstract class FragmentMapRotation extends Fragment {

	protected Typeface typeface;
	protected Schedule schedule;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		typeface = Typeface.createFromAsset(getContext().getAssets(), "project_paintball_beta_2.otf");
		schedule = (Schedule) getArguments().getSerializable("schedule");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_schedule, container, false);

		TextView txtGameMode = (TextView) view.findViewById(R.id.game_mode_title);
		txtGameMode.setTypeface(typeface);

		ImageView imgGameModeIcon = (ImageView) view.findViewById(R.id.game_mode_icon);
		imgGameModeIcon.setImageResource(getActiveMode().getIconResId());

		ViewGroup stagesContainer = (ViewGroup) view.findViewById(R.id.stages_container);
		stagesContainer.removeAllViews();

		for(Stage stage : getActiveMode().getStages()) {
			View stageView = inflater.inflate(R.layout.stage_card, stagesContainer, false);

			TextView txtMapName = (TextView) stageView.findViewById(R.id.txt_map_title);
			ImageView imgMapPreview = (ImageView) stageView.findViewById(R.id.img_stage);

			txtMapName.setTypeface(typeface);

			txtMapName.setText(stage.getNameResId());
			imgMapPreview.setImageResource(stage.getPreviewResId());
			stagesContainer.addView(stageView);
		}

		return view;
	}

	protected abstract GameMode getActiveMode();

}
