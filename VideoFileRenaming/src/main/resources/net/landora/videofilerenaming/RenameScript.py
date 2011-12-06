from net.landora.videoinfo import VideoInfoFileUtils

def replaceIllegalCharacters(inputString):
   return VideoInfoFileUtils.filterInvalidFilenameCharacters(inputString)
