eventTestPhaseStart = { phaseName ->
	def testTemplateSrcDir = 'test/conf'
	if(new File(testTemplateSrcDir).exists()) {
		junitReportStyleDir = testTemplateSrcDir
	}
}

