from net.landora.video.filerenaming import RenameScriptFunctions

def replaceIllegalCharacters(inputString):
   return RenameScriptFunctions.filterInvalidFilenameCharacters(inputString)

def getSharedDirectoryPath(inputString):
   return RenameScriptFunctions.getSharedDirectoryPath(inputString)
