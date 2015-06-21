package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import euphoriadigital.karaoke.R;

public class ViewVideoFragment extends Fragment implements RecordMovieActionTaker {

    @InjectView(R.id.videoview) VideoView videoView;

    private Controller controller;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Controller)) {
            throw new ClassCastException("Activity must implement " + Controller.class);
        }
        controller = (Controller) activity;
        controller.registerActionTaker(this);
    }

    @Override
    public void onDetach() {
        controller.unregisterActionTaker();
        controller = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_movie, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.showVideo();
    }

    @OnClick(R.id.button_delete)
    void onButtonDeleteClick () {
        controller.deleteVideo();
    }

    @OnClick(R.id.button_save)
    void onButtonSaveClick() {
        controller.saveVideo();
    }

    @Override
    public void showMovie(Uri videoUri) {
        if (videoView != null) {
            videoView.setVideoURI(videoUri);
            videoView.setMediaController(new MediaController(getActivity()));
        }
    }

    public interface Controller {
        void showVideo();

        void deleteVideo();

        void saveVideo();

        void registerActionTaker(RecordMovieActionTaker actionTaker);

        void unregisterActionTaker();
    }
}
