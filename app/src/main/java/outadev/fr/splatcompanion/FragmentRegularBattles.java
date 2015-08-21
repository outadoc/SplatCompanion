package outadev.fr.splatcompanion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import outadev.fr.splatcompanion.model.GameModeRegular;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.Stage;

/**
 * Created by outadoc on 21/08/15.
 */
public class FragmentRegularBattles extends FragmentMapRotation {

	private List<Stage> stages;
	private GameModeRegular mode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Schedule schedule = (Schedule) getArguments().getSerializable("schedule");
		mode = schedule.getRegularMode();
		stages = mode.getStages();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		TextView txtGameModeTitle = (TextView) view.findViewById(R.id.game_mode_title);
		ImageView imgGameModeIcon = (ImageView) view.findViewById(R.id.game_mode_icon);

		txtGameModeTitle.setText(R.string.rules_turf_war);
		imgGameModeIcon.setImageResource(mode.getIconResId());

		ViewGroup stagesContainer = (ViewGroup) view.findViewById(R.id.stages_container);
		stagesContainer.removeAllViews();

		for(Stage stage : stages) {
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

}
