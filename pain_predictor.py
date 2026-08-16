import cv2
import mediapipe as mp
import numpy as np

# 使用新版 MediaPipe Tasks API
from mediapipe.tasks import python
from mediapipe.tasks.python import vision

# 初始化 Face Landmarker
base_options = python.BaseOptions(model_asset_path='face_landmarker.task')
options = vision.FaceLandmarkerOptions(
    base_options=base_options,
    output_face_blendshapes=False,
    output_facial_transformation_matrixes=False,
    num_faces=1
)
landmarker = vision.FaceLandmarker.create_from_options(options)

def calculate_distance(point1, point2):
    """计算两点之间的欧氏距离"""
    return np.linalg.norm(np.array(point1) - np.array(point2))

def extract_geometric_features(landmarks, image_shape):
    """从MediaPipe关键点中提取几何特征"""
    h, w, _ = image_shape
    
    def get_coord(idx):
        lm = landmarks[idx]
        return np.array([lm.x * w, lm.y * h])
    
    features = []
    
    # 左眉和右眉之间的距离
    left_eyebrow = get_coord(55)
    right_eyebrow = get_coord(285)
    eyebrow_distance = calculate_distance(left_eyebrow, right_eyebrow)
    features.append(eyebrow_distance)
    
    # 嘴部宽度
    left_mouth = get_coord(61)
    right_mouth = get_coord(291)
    mouth_width = calculate_distance(left_mouth, right_mouth)
    features.append(mouth_width)
    
    return np.array(features)

def estimate_au_intensity(features):
    """根据几何特征估算各AU强度 (0-5)"""
    au4_intensity = 0
    au15_intensity = 0
    au6_intensity = 0
    au7_intensity = 0
    au9_intensity = 0
    au10_intensity = 0
    au43_intensity = 0
    
    if len(features) > 0:
        if features[0] > 100:
            au4_intensity = min(5, int((features[0] - 100) / 10))
    
    if len(features) > 1:
        if features[1] < 30:
            au15_intensity = min(5, int((30 - features[1]) / 5))
    
    return au4_intensity, au15_intensity, au6_intensity, au7_intensity, au9_intensity, au10_intensity, au43_intensity

def predict_pain_level(image_path):
    """主函数：预测图片中的疼痛等级"""
    try:
        img = cv2.imread(image_path)
        if img is None:
            print(f"无法读取图片: {image_path}")
            return None
            
        # 转换为RGB并创建MediaPipe Image对象
        img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        mp_image = mp.Image(image_format=mp.ImageFormat.SRGB, data=img_rgb)
        
        # 检测面部关键点
        detection_result = landmarker.detect(mp_image)
        
        if not detection_result.face_landmarks:
            print(f"未检测到人脸: {image_path}")
            return None
        
        landmarks = detection_result.face_landmarks[0]
        features = extract_geometric_features(landmarks, img.shape)
        
        au4, au15, au6, au7, au9, au10, au43 = estimate_au_intensity(features)
        
        pspi_score = au4 + max(au6, au7) + max(au9, au10) + au43
        
        if pspi_score <= 3:
            pain_level = 0
        elif pspi_score <= 6:
            pain_level = 1
        elif pspi_score <= 9:
            pain_level = 2
        elif pspi_score <= 12:
            pain_level = 3
        else:
            pain_level = 4
        
        return pspi_score, pain_level, features, (au4, au15, au6, au7, au9, au10, au43)
        
    except Exception as e:
        print(f"预测过程中出错: {e}")
        return None

if __name__ == "__main__":
    test_image = "test.jpg"
    result = predict_pain_level(test_image)
    if result:
        pspi_score, pain_level, features, au_intensities = result
        level_names = ["无痛", "轻度疼痛", "中度疼痛", "重度疼痛", "剧痛"]
        print(f"PSPI得分: {pspi_score}")
        print(f"疼痛等级: {pain_level} - {level_names[pain_level]}")
        print(f"特征参数: {features}")
        print(f"AU强度: {au_intensities}")
    else:
        print("预测失败")

        