if metadata.isMovie():
	coreName = metadata.getMovieName()
else:
	coreName = metadata.getSeriesName()

if metadata.isAdult():
	return "/var/storage/blakes/Hentai/" + replaceIllegalCharacters(coreName)
else:
	return "/var/storage/Videos/Anime/" + replaceIllegalCharacters(coreName)