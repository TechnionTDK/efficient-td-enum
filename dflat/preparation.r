library(data.table)

arguments <- commandArgs(trailingOnly = TRUE)

if (length(arguments) >= 1)
{
    input <- data.table(read.csv(arguments[1], na.strings = c("UNKNOWN", "INFINITE", "?")))

    columns <- ncol(input)

    for (c in 2:columns)
    {
	input[, c] <- as.numeric(unlist(input[, c, with=FALSE]))
    }

    instances <- unique(input$Instance)

    for (instance in instances)
    {
	tmp <- input[Instance==as.character(instance)]$UserTime
	
	#Set to 5 for testing purposes. Set limit to number of tdId per instance to retain only those instances where no timeout was observed among the seeds used.
	if (sum(!is.na(tmp)) >= 5)
	{
	    minimum <- min(tmp, na.rm=TRUE)
	    maximum <- max(tmp, na.rm=TRUE)
		
	    if (maximum - minimum < 1)
	    {
		input <- input[Instance!=as.character(instance)]
	    }
	}
	else
	{
	    input <- input[Instance!=as.character(instance)]
	}
    }

    output.cleaned <- input
    output.standardized <- input

    instances <- unique(output.standardized$Instance)

    for (instance in instances)
    {
	tmp <- data.table(scale(output.standardized[Instance == as.character(instance)][,3:columns, with=FALSE]))
	
	for (c in 1:(columns-2))
	{
	    if (nrow(unique(tmp[,c,with=FALSE])) <= 1) 
	    {
		tmp[,c] <- rep(0, nrow(tmp[,c, with=FALSE]))
	    }
	}
	
	output.standardized[Instance == instance][,3:columns] <- tmp
    }

    output.cleaned <- output.cleaned[, Instance := NULL]
    output.standardized <- output.standardized[, Instance := NULL]

	filename<-sub('.csv$','',arguments[1])
    write.csv(output.cleaned, paste(filename,"results_cleaned.csv",sep="_"), row.names=FALSE, na="?")
    write.csv(output.standardized, paste(filename,"results_standardized.csv",sep="_"), row.names=FALSE, na="?")
}

q(save="no")
