# MV - Machine Vision

**SAR Change Detection Using Deep Learning: A Replication Study**

This project replicates the study "Improved Difference Images for Change Detection Classifiers in SAR Imagery Using Deep Learning" by Alatalo et al., focusing on neural network-based mapping transformation for enhanced SAR change detection.

## 🎯 Project Overview

The project addresses change detection in SAR imagery for environmental monitoring and disaster assessment. The replication adapts the original methodology to work within computational constraints while maintaining the core research objectives.

### Key Features
- **SAR Image Preprocessing**: Speckle noise reduction, geometric corrections, and radiometric normalization
- **Deep Learning-Based DI Formation**: Neural network mapping transformation instead of traditional algebraic operations
- **Change Detection Classification**: Comparison between threshold-based and SVM classifiers
- **Computational Adaptation**: Scaled implementation for resource-constrained environments

## 📊 Research Methodology

**Dataset**: Sentinel-1 SAR imagery from North-East Finland, with environmental data from FMI and DEM from NLS.

**Key Steps**:
1. **Preprocessing**: Speckle noise reduction using neural networks, geometric corrections, and radiometric normalization
2. **Deep Learning DI Formation**: Neural network trained to generate artificial SAR images matching acquisition conditions
3. **Change Detection**: Classification using threshold-based and SVM methods
4. **Evaluation**: Performance comparison using ROC curves and AUC scores

### Computational Adaptations
Due to hardware constraints, the replication used:
- Reduced dataset (130 parts vs. full dataset)
- Smaller batch size (8 vs. optimized GPU batch size)
- Extended training time (several hours vs. accelerated GPU processing)
- CPU-based processing instead of GPU acceleration

## 📈 Research Results

### Training Performance
- **Training Loss Curves**: Stable downward trends indicating proper learning
- **MSE (Mean Squared Error)**: Decreased over epochs, showing model improvement
- **Forest Change Detection**: Progressive improvement through training iterations

### Key Findings from Original Study
- **Threshold Classifier**: Deep learning-enhanced DI (AUC: 0.87) vs. Traditional DI (AUC: 0.79)
- **SVM Classifier**: Higher accuracy and lower false detection rates with deep learning approach

## 🏗️ Project Structure

```
mv/
├── MV_Project_Paper.pdf            # Comprehensive research paper
│                                   # - SAR change detection methodology
│                                   # - Deep learning implementation
│                                   # - Replication study results
│                                   # - Performance analysis
```

## 📄 Assignment Details

This project was completed as part of the **MV (Machine Vision)** course at the University of Maribor, Faculty of Electrical Engineering and Computer Science (FERI).

**Course**: MV - Machine Vision  
**Institution**: University of Maribor, FERI  
**Focus**: SAR image processing, deep learning applications, and research replication methodology

---

*This project showcases the practical implementation of advanced SAR change detection using deep learning techniques, demonstrating the feasibility of state-of-the-art remote sensing methods in resource-constrained environments.*