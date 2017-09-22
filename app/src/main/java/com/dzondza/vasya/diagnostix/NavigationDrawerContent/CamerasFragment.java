package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;
import java.util.List;


/**
 * contains cameras' information
 */

public class CamerasFragment extends BaseDetailedFragment {
    private Camera mCamera;
    private Camera.Parameters mParameters;

    private String mFocalLength, mSupportedFaces, mMaxZoom;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // activates recyclerView
        initializeRecyclerView(view);

        recyclerListData();

        getActivity().setTitle(R.string.drawer_camera);

        return view;
    }


    // gets camera2 Api's parameters
    private void lollipopCameraParams(String camId, List<RecyclerItemsData> dataList) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager cameraManager = (CameraManager)
                    getActivity().getSystemService(Context.CAMERA_SERVICE);

            try {
                CameraCharacteristics backCharacter = cameraManager.getCameraCharacteristics(camId);

                mFocalLength = String.valueOf(backCharacter.get
                        (CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)[0]);
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_focal_length),
                        mFocalLength));

                if ((backCharacter.get(CameraCharacteristics
                        .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM) != null)) {
                    String digitZoom = String.valueOf(backCharacter.get
                            (CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM));
                    recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_max_digit_zoom),
                            digitZoom));
                }


                String awbAreas = String.valueOf(backCharacter.get
                        (CameraCharacteristics.CONTROL_MAX_REGIONS_AWB));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_awb_metering_areas),
                        awbAreas));


                String aeAreas = String.valueOf(backCharacter.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_ae_metering_areas),
                        aeAreas));


                String afAreas = String.valueOf(backCharacter.get
                        (CameraCharacteristics.CONTROL_MAX_REGIONS_AF));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_af_metering_areas),
                        afAreas));


                String flashSolution = String.valueOf(backCharacter.get
                        (CameraCharacteristics.FLASH_INFO_AVAILABLE));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_flash_unit),
                        flashSolution));


                mMaxZoom = String.valueOf(backCharacter.get
                        (CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_max_zoom_value),
                        mMaxZoom));


                String cropType;
                switch (backCharacter.get(CameraCharacteristics.SCALER_CROPPING_TYPE)) {
                    case CameraMetadata.SCALER_CROPPING_TYPE_CENTER_ONLY:
                        cropType = getString(R.string.camera_centered);
                        break;
                    case CameraMetadata.SCALER_CROPPING_TYPE_FREEFORM:
                        cropType = getString(R.string.camera_arbitrarily_chosen);
                        break;
                    default:
                        cropType = getString(R.string.unknown);
                }
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_crop_type),
                        cropType));


                String pixelSize = new StringBuilder().append(0.01 * Math.round(100 *
                        backCharacter.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
                                .getWidth())).append(" x ").append(0.01 * Math.round(100 *
                        backCharacter.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
                                .getHeight())).append(" mm").toString();
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_sensor_pixel_array_size),
                        pixelSize));


                String resolution = new StringBuilder().append(backCharacter.get
                        (CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE).getWidth())
                        .append(" x ").append(backCharacter.get(CameraCharacteristics
                                .SENSOR_INFO_PIXEL_ARRAY_SIZE).getHeight()).toString();
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.resolution), resolution));


                String timestampsSource;
                Integer sensorTimestampSource = backCharacter.get(CameraCharacteristics
                        .SENSOR_INFO_TIMESTAMP_SOURCE);
                if (sensorTimestampSource == CameraMetadata.SENSOR_INFO_TIMESTAMP_SOURCE_REALTIME) {
                    timestampsSource = getString(R.string.camera_real_time);
                } else {
                    timestampsSource = getString(R.string.unknown);
                }
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_timestamps_source),
                        timestampsSource));


                mSupportedFaces = String.valueOf(backCharacter.get
                        (CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT));
                recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_supported_detected_faces),
                        mSupportedFaces));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //gets mCamera Api's parameters(for android versions before lollipop)
    private void oldCameraParams(int cameraId, List<RecyclerItemsData> dataList) {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(cameraId);
                mParameters = mCamera.getParameters();
            }

            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_color_effect),
                    mParameters.getColorEffect()));


            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_flash_mode),
                    mParameters.getFlashMode()));


            mFocalLength = String.valueOf(mParameters.getFocalLength()).concat(" mm");
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_focal_length),
                    mFocalLength));


            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_focus_mode),
                    mParameters.getFocusMode()));


            String horizAngle = String.valueOf(mParameters.getHorizontalViewAngle()).concat(" degrees");
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_horizontal_angle),
                    horizAngle));


            String verticAngle = String.valueOf(mParameters.getVerticalViewAngle()).concat(" degrees");
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_vertical_angle),
                    verticAngle));


            String jpegQuality = String.valueOf(mParameters.getJpegQuality());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_jpeg_picture_quality),
                    jpegQuality));


            String thumbnailSize = "" + mParameters.getJpegThumbnailSize().width + " x "
                    + mParameters.getJpegThumbnailSize().height;
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_thumbnail_picture_size),
                    thumbnailSize));


            String maxExposure = String.valueOf(mParameters.getMaxExposureCompensation());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_max_exposure_compensation),
                    maxExposure));


            String minExposure = String.valueOf(mParameters.getMinExposureCompensation());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_min_exposure_compensation),
                    minExposure));


            mSupportedFaces = String.valueOf(mParameters.getMaxNumDetectedFaces());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_supported_detected_faces),
                    mSupportedFaces));


            String focusAreas = String.valueOf(mParameters.getMaxNumFocusAreas());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_supported_focus_areas),
                    focusAreas));


            String meteringAreasSolutions = String.valueOf(mParameters.getMaxNumMeteringAreas());
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_supported_metering_areas),
                    meteringAreasSolutions));


            if (mParameters.isZoomSupported()) {
                mMaxZoom = String.valueOf(mParameters.getMaxZoom());
            } else {
                mMaxZoom = getString(R.string.camera_not_supported);
            }
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_max_zoom_value),
                    mMaxZoom));


            String imageFormat;
            switch (mParameters.getPictureFormat()) {
                case ImageFormat.RAW_PRIVATE:
                case ImageFormat.RAW_SENSOR:
                case ImageFormat.RAW12:
                case ImageFormat.RAW10:
                    imageFormat = getString(R.string.camera_raw);
                    break;
                case ImageFormat.FLEX_RGBA_8888:
                    imageFormat = getString(R.string.camera_rgba);
                    break;
                case ImageFormat.FLEX_RGB_888:
                    imageFormat = getString(R.string.camera_rgb);
                    break;
                case ImageFormat.JPEG:
                    imageFormat = getString(R.string.camera_jpeg);
                    break;
                default:
                    imageFormat = getString(R.string.unknown);
            }
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_image_format),
                    imageFormat));


            String imageDimension = "" + mParameters.getPictureSize().width + " x "
                    + mParameters.getPictureSize().height;
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_image_dimension),
                    imageDimension));


            String videoDimension = "" + mParameters.getPreferredPreviewSizeForVideo().width + " x "
                    + mParameters.getPreferredPreviewSizeForVideo().height;
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_video_dimension),
                    videoDimension));


            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_scene_mode),
                    mParameters.getSceneMode()));


            String videoStabilization;
            if (mParameters.isVideoStabilizationSupported()) {
                videoStabilization = getString(R.string.camera_supported);
            } else
                videoStabilization = getString(R.string.camera_not_supported);
            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_video_stabilization),
                    videoStabilization));


            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_white_balance),
                    mParameters.getWhiteBalance()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCamera != null) {
                mCamera.release();
            }
            mCamera = null;
        }
    }

    @Override
    protected void recyclerListData() {

        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_rear_camera),
                getString(R.string.camera_characteristics)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lollipopCameraParams("0", recyclerViewLine);

            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_front_camera),
                    getString(R.string.camera_characteristics)));
            lollipopCameraParams("1", recyclerViewLine);

        } else {
            oldCameraParams(0, recyclerViewLine);

            recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_front_camera),
                    getString(R.string.camera_characteristics)));
            oldCameraParams(1, recyclerViewLine);
        }
    }
}