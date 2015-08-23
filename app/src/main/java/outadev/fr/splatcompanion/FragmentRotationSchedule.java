package outadev.fr.splatcompanion;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import outadev.fr.splatcompanion.model.GameMode;
import outadev.fr.splatcompanion.model.Schedule;
import outadev.fr.splatcompanion.model.Stage;

/**
 * A base fragment that presents a rotation schedule to the user.
 */
public abstract class FragmentRotationSchedule extends Fragment {

	public static final String KEY_SCHEDULE = "schedule";

	protected Typeface typeface;
	protected Schedule schedule;

	protected TextView txtGameMode;
	protected ImageView imgGameModeIcon;
	protected ViewGroup stagesContainer;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		typeface = Typeface.createFromAsset(getContext().getAssets(), "project_paintball_beta_2.otf");

		if(savedInstanceState != null && savedInstanceState.containsKey(KEY_SCHEDULE)) {
			schedule = (Schedule) savedInstanceState.getSerializable(KEY_SCHEDULE);
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_schedule, container, false);

		txtGameMode = (TextView) view.findViewById(R.id.game_mode_title);
		imgGameModeIcon = (ImageView) view.findViewById(R.id.game_mode_icon);
		stagesContainer = (ViewGroup) view.findViewById(R.id.stages_container);

		txtGameMode.setTypeface(typeface);

		if(schedule != null) {
			displaySchedule();
		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(KEY_SCHEDULE, schedule);
	}

	/**
	 * Gets the active game mode for this fragment (regular or ranked).
	 * @return
	 */
	protected abstract GameMode getActiveMode();

	/**
	 * Displays the current stages on the view.
	 * schedule must not be null
	 */
	protected void displaySchedule() {
		if(schedule == null) {
			Log.e(FragmentRotationSchedule.class.getName(), "displaySchedule called with schedule == null");
			return;
		}

		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		imgGameModeIcon.setImageResource(getActiveMode().getIconResId());
		stagesContainer.removeAllViews();

		for(Stage stage : getActiveMode().getStages()) {
			View stageView = inflater.inflate(R.layout.stage_card, stagesContainer, false);

			TextView txtMapName = (TextView) stageView.findViewById(R.id.txt_map_title);
			ImageView imgMapPreview = (ImageView) stageView.findViewById(R.id.img_stage);

			txtMapName.setText(stage.getNameResId());
			txtMapName.setTypeface(typeface);

			imgMapPreview.setImageResource(stage.getPreviewResId());
			stagesContainer.addView(stageView);
		}
	}

	/**
	 * Updates the schedule. The view will be updated as soon as possible.
	 * @param schedule
	 */
	protected void updateSchedule(Schedule schedule) {
		this.schedule = schedule;

		if(getView() != null) {
			displaySchedule();
		}
	}

}
