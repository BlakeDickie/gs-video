if metadata.isMovie():
	return replaceIllegalCharacters(metadata.getMovieName())
else:
	return replaceIllegalCharacters(
		metadata.getSeriesName() + " - " + metadata.getEpisodeNumber() + " - " + metadata.getEpisodeName())