from net.landora.video.info import VideoInfoFileUtils

def replaceIllegalCharacters(inputString):
   return VideoInfoFileUtils.filterInvalidFilenameCharacters(inputString)
