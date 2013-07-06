package de.rmuselmann.logic.impl;

import java.io.File;
import java.util.LinkedList;

import de.rmuselmann.logic.IFoundListener;


public class FileFinder {

	public LinkedList<File> findings = new LinkedList<File>();

	public void sucheDatei(String name, File directory, IFoundListener fnd) {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file != null && file.exists()) {
				if (file.getName().contains(name)) {
					findings.add(file);
					if (fnd != null) {
						fnd.fileFound(file);
					}
				}
				if (file.isDirectory()) {
					sucheDatei(name, file, fnd);
				}
			}
		}
	}

	public void sucheDateiMitRegEx(String regEx, File directory,
			IFoundListener fnd) {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file != null && file.exists()) {
				if (file.getName().matches(regEx)) {
					findings.add(file);
					if (fnd != null) {
						fnd.fileFound(file);
					}
				}
				if (file.isDirectory()) {
					sucheDateiMitRegEx(regEx, file, fnd);
				}
			}
		}
	}

	public void sucheDateiNamen(String name, File directory, IFoundListener fnd) {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file != null && file.exists()) {
				if (file.getName().equals(name)) {
					findings.add(file);
					if (fnd != null) {
						fnd.fileFound(file);
					}
				}
				if (file.isDirectory()) {
					sucheDateiNamen(name, file, fnd);
				}
			}
		}
	}

}