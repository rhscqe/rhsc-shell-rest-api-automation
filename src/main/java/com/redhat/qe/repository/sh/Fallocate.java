package com.redhat.qe.repository.sh;

import com.redhat.qe.helpers.utils.AbsolutePath;
import com.redhat.qe.helpers.utils.FileSize;

public class Fallocate {
	public void creteEmptyFile(FileSize filesize, AbsolutePath file){
		String.format("fallocate -l %sK %s", filesize.toKilobytes(), file.toString());
	}
}
