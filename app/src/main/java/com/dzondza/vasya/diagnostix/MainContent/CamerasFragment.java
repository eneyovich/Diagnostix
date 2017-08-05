package com.dzondza.vasya.diagnostix.MainContent;

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
    private Camera camera;
    private Camera.Parameters parameters;

    private String focalLengthDescript, focalLength;
    private String SupportedFacesDescript, SupportedFaces;
    private String maxZoomDescript, maxZoom;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // init recyclerView List
        initializeRecyclerView(view);


        recyclerViewLine.add(new RecyclerItemsData(getString(R.string.camera_rear_camera), getString(R.string.camera_characteristics)));

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


        //toolbar title
        getActivity().setTitle(R.string.drawer_camera);

        return view;
    }


    // camera2 api params
    private void lollipopCameraParams(String camId, List<RecyclerItemsData>dataList) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager cameraManager = (CameraManager)
                    getActivity().getSystemService(Context.CAMERA_SERVICE);

            try {
                CameraCharacteristics backCharacter = cameraManager.getCameraCharacteristics(camId);

                focalLengthDescript= getString(R.string.camera_focal_length);
                focalLength = String.valueOf(backCharacter.get
                        (CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)[0]);
                recyclerViewLine.add(new RecyclerItemsData(focalLengthDescript, focalLength));


                if ((backCharacter.get(CameraCharacteristics
                        .SCALER_AVAILABLE_MAX_DIGITAL_ZOOM) != null)) {
                    String digitZoomDescript = getString(R.string.camera_max_digit_zoom);
                    String digitZoom = String.valueOf(backCharacter.get
                            (CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM));
                    recyclerViewLine.add(new RecyclerItemsData(digitZoomDescript, digitZoom));
                }


                String awbDescript = getString(R.string.camera_awb_metering_areas);
                String awbSolution = String.valueOf(backCharacter.get
                        (CameraCharacteristics.CONTROL_MAX_REGIONS_AWB));
                recyclerViewLine.add(new RecyclerItemsData(awbDescript, awbSolution));


                String aeDescript = getString(R.string.camera_ae_metering_areas);
                String aeSolution = String.valueOf(backCharacter.get
                        (CameraCharacteristics.CONTROL_MAX_REGIONS_AE));
                recyclerViewLine.add(new RecyclerItemsData(aeDescript, aeSolution));


                String afDescript = getString(R.string.camera_af_metering_areas);
                String afSolution = String.valueOf(backCharacter.get
                        (CameraCharacteristics.CONTROL_MAX_REGIONS_AF));
                recyclerViewLine.add(new RecyclerItemsData(afDescript, afSolution));


                String flashDescript = getString(R.string.camera_flash_unit);
                String flashSolution = String.valueOf(backCharacter.get
                        (CameraCharacteristics.FLASH_INFO_AVAILABLE));
                recyclerViewLine.add(new RecyclerItemsData(flashDescript, flashSolution));


                maxZoomDescript = getString(R.string.camera_max_zoom_value);
                maxZoom = String.valueOf(backCharacter.get
                        (CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM));
                recyclerViewLine.add(new RecyclerItemsData(maxZoomDescript, maxZoom));


                String cropTypeDescript = getString(R.string.camera_crop_type);
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
                recyclerViewLine.add(new RecyclerItemsData(cropTypeDescript, cropType));


                String pixelSizeDescript = getString(R.string.camera_sensor_pixel_array_size);
                String pixelSize = new StringBuilder().append(0.01 * Math.round(100 *
                        backCharacter.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
                                .getWidth())).append(" x ").append(0.01 * Math.round(100 *
                        backCharacter.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE)
                                .getHeight())).append(" mm").toString();
                recyclerViewLine.add(new RecyclerItemsData(pixelSizeDescript, pixelSize));


                String resolutionDescript = getString(R.string.resolution);
                String resolution = new StringBuilder().append(backCharacter.get
                        (CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE).getWidth())
                        .append(" x ").append(backCharacter.get(CameraCharacteristics
                                .SENSOR_INFO_PIXEL_ARRAY_SIZE).getHeight()).toString();
                recyclerViewLine.add(new RecyclerItemsData(resolutionDescript, resolution));


                String timestampsSourceDescript = getString(R.string.camera_timestamps_source);
                String timestampsSource;
                Integer sensorTimestampSource = backCharacter.get(CameraCharacteristics
                        .SENSOR_INFO_TIMESTAMP_SOURCE);
                if (sensorTimestampSource == CameraMetadata.SENSOR_INFO_TIMESTAMP_SOURCE_REALTIME) {
                    timestampsSource = getString(R.string.camera_real_time);
                } else {
                    timestampsSource = getString(R.string.unknown);
                }
                recyclerViewLine.add(new RecyclerItemsData(timestampsSourceDescript, timestampsSource));


                SupportedFacesDescript = getString(R.string.camera_supported_detected_faces);
                SupportedFaces = String.valueOf(backCharacter.get
                        (CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT));
                recyclerViewLine.add(new RecyclerItemsData(SupportedFacesDescript, SupportedFaces));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //camera api params(before lollipop)
    private void oldCameraParams(int cameraId, List<RecyclerItemsData>dataList) {
        try {
            if (camera == null) {
                camera = Camera.open(cameraId);
                parameters = camera.getParameters();
            }

            String colorEffect = getString(R.string.camera_color_effect);
            recyclerViewLine.add(new RecyclerItemsData(colorEffect, parameters.getColorEffect()));


            String flashMode = getString(R.string.camera_flash_mode);
            recyclerViewLine.add(new RecyclerItemsData(flashMode, parameters.getFlashMode()));


            focalLengthDescript = getString(R.string.camera_focal_length);
            focalLength = String.valueOf(parameters.getFocalLength()).concat(" mm");
            recyclerViewLine.add(new RecyclerItemsData(focalLengthDescript, focalLength));


            String focusMode = getString(R.string.camera_focus_mode);
            recyclerViewLine.add(new RecyclerItemsData(focusMode, parameters.getFocusMode()));


            String horizAngleDescript = getString(R.string.camera_horizontal_angle);
            String horizAngle = String.valueOf(parameters.getHorizontalViewAngle()).concat(" degrees");
            recyclerViewLine.add(new RecyclerItemsData(horizAngleDescript, horizAngle));


            String verticAngleDescript = getString(R.string.camera_vertical_angle);
            String verticAngle = String.valueOf(parameters.getVerticalViewAngle()).concat(" degrees");
            recyclerViewLine.add(new RecyclerItemsData(verticAngleDescript, verticAngle));


            String jpegQualityDescript = getString(R.string.camera_jpeg_picture_quality);
            String jpegQuality = String.valueOf(parameters.getJpegQuality());
            recyclerViewLine.add(new RecyclerItemsData(jpegQualityDescript, jpegQuality));


            String thumbnailSizeDescript = getString(R.string.camera_thumbnail_picture_size);
            String thumbnailSize = "" + parameters.getJpegThumbnailSize().width + " x "
                    + parameters.getJpegThumbnailSize().height;
            recyclerViewLine.add(new RecyclerItemsData(thumbnailSizeDescript, thumbnailSize));


            String maxExposureDescript = getString(R.string.camera_max_exposure_compensation);
            String maxExposure = String.valueOf(parameters.getMaxExposureCompensation());
            recyclerViewLine.add(new RecyclerItemsData(maxExposureDescript, maxExposure));


            String minExposureDescript = getString(R.string.camera_min_exposure_compensation);
            String minExposure = String.valueOf(parameters.getMinExposureCompensation());
            recyclerViewLine.add(new RecyclerItemsData(minExposureDescript, minExposure));


            SupportedFacesDescript = getString(R.string.camera_supported_detected_faces);
            SupportedFaces = String.valueOf(parameters.getMaxNumDetectedFaces());
            recyclerViewLine.add(new RecyclerItemsData(SupportedFacesDescript, SupportedFaces));


            String focusAreasDescriptions = getString(R.string.camera_supported_focus_areas);
            String focusAreas = String.valueOf(parameters.getMaxNumFocusAreas());
            recyclerViewLine.add(new RecyclerItemsData(focusAreasDescriptions, focusAreas));


            String meteringAreasDescriptions = getString(R.string.camera_supported_metering_areas);
            String meteringAreasSolutions = String.valueOf(parameters.getMaxNumMeteringAreas());
            recyclerViewLine.add(new RecyclerItemsData(meteringAreasDescriptions, meteringAreasSolutions));


            maxZoomDescript = getString(R.string.camera_max_zoom_value);
            if (parameters.isZoomSupported()) {
                maxZoom = String.valueOf(parameters.getMaxZoom());
            } else {
                maxZoom = getString(R.string.camera_not_supported);
            }
            recyclerViewLine.add(new RecyclerItemsData(maxZoomDescript, maxZoom));


            String imageFormatDescript = getString(R.string.camera_image_format);
            String imageFormat;
            switch (parameters.getPictureFormat()) {
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
            recyclerViewLine.add(new RecyclerItemsData(imageFormatDescript, imageFormat));


            String imageDimensionDescript = getString(R.string.camera_image_dimension);
            String imageDimension = "" + parameters.getPictureSize().width + " x "
                    + parameters.getPictureSize().height;
            recyclerViewLine.add(new RecyclerItemsData(imageDimensionDescript, imageDimension));


            String videoDimensionDescript = getString(R.string.camera_video_dimension);
            String videoDimension = "" + parameters.getPreferredPreviewSizeForVideo().width + " x "
                    + parameters.getPreferredPreviewSizeForVideo().height;
            recyclerViewLine.add(new RecyclerItemsData(videoDimensionDescript, videoDimension));


            String sceneMode = getString(R.string.camera_scene_mode);
            recyclerViewLine.add(new RecyclerItemsData(sceneMode, parameters.getSceneMode()));


            String videoStabilizatDescript = getString(R.string.camera_video_stabilization);
            String videoStabilizat;
            if (parameters.isVideoStabilizationSupported()) {
                videoStabilizat = getString(R.string.camera_supported);
            } else
                videoStabilizat = getString(R.string.camera_not_supported);
            recyclerViewLine.add(new RecyclerItemsData(videoStabilizatDescript, videoStabilizat));


            String whiteBalance = getString(R.string.camera_white_balance);
            recyclerViewLine.add(new RecyclerItemsData(whiteBalance, parameters.getWhiteBalance()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (camera != null) {
                camera.release();
            }
            camera = null;
        }
    }
}