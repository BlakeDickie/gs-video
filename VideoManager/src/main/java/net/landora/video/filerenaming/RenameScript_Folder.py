if metadata.isMovie():
	coreName = metadata.getMovieName()
else:
	coreName = metadata.getSeriesName()

if metadata.isAdult():
	return getSharedDirectoryPath("Quon Hentai") + replaceIllegalCharacters(coreName)
else:
	return getSharedDirectoryPath("Quon Storage Videos") + "Anime/" + replaceIllegalCharacters(coreName)
