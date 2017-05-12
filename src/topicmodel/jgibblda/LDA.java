/*
 * Copyright (C) 2007 by
 * 
 * 	Xuan-Hieu Phan
 *	hieuxuan@ecei.tohoku.ac.jp or pxhieu@gmail.com
 * 	Graduate School of Information Sciences
 * 	Tohoku University
 * 
 *  Cam-Tu Nguyen
 *  ncamtu@gmail.com
 *  College of Technology
 *  Vietnam National University, Hanoi
 *
 * JGibbsLDA is a free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JGibbsLDA is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JGibbsLDA; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package topicmodel.jgibblda;


import org.kohsuke.args4j.*;

public class LDA {
	/*
	 * LDA模型，训练时使用：lda -est [-alpha <double>] [-beta <double>] [-ntopics
<int>] [-niters <int>] [-savestep <int>] [-twords <int>] -
dfile <string>
输入文件格式为：[M]
[document1]
[document2]
...
[documentM]
其中：
[documenti] = [wordi1] [wordi2] ... [wordiNi]，每个词用空格分隔
输出为几个文件，<model_name>.others：包含alpha=?beta=?ntopics=? ndocs=? nwords=?liter=?信息
<model_name>.phi :写的是word-topic分布，每行为一个topic,每列为语料中出现的词。概率为：p(wordw|topict)   其中Nwi表示对应到topict的VOC中第i个单词的数目，N表示所有对应到topict的单词总数。
<model_name>.theta: topic-document distributions, p(topict|documentm)，每一行为一个document,每一列为一个topic  第i个topic的词的数目，n是d中所有词的总数
<model_name>.tassign:This file contains the topic assignments for words in training
data. 每一行为一个文档的词，用<wordij>:<topic of wordij>表示，其中wordij，表示词在wordmap中的序号，<topic of wordij>指这个词指的是哪个主题
<model_name>.twords:This file contains twords most likely words of each topic.
       也可以追加训练：lda -estc -dir <string> -model <string> [-niters <int>] -savestep <int>] [-twords <int>]
   测试时使用：           lda -inf -dir <string> -model <string> [-niters <int>] [-twords <int>] -dfile <string>
    例如：                    LDA -est -alpha 0.5 -beta 0.1 -ntopics 100 -niters 1000 -savestep 100 -twords 20 -dfile models/casestudy/
trndocs.dat
LDA -estc -dir models/casestudy/ -model model-01000 -niters 800 -savestep 100 -twords 30
LDA -inf -dir models/casestudy/ -model model-01800 -niters 30 -twords 20 -dfile newdocs.dat	
测试完成后生成：newdocs.dat.others
newdocs.dat.phi
newdocs.dat.tassign
newdocs.dat.theta
newdocs.dat.twords		
	
	
*/	public static void myLDA(String args[]){
		/*	String aa="-est -alpha 0.5 -beta 0.1 -ntopics 100 -niters 200 -savestep 100 "+ 
       "-twords 20 -dfile models\\trndocs.dat";
		
	args=aa.split(" ");*/

//	String aa="-inf -dir models\\ -model model-final -niters 30 -twords 20 -dfile newdocs.dat";

		LDACmdOption option = new LDACmdOption();
		CmdLineParser parser = new CmdLineParser(option);
		
		try {
			if (args.length == 0){
				showHelp(parser);
				return;
			}
			
			parser.parseArgument(args);
			
			if (option.est || option.estc){
				Estimator estimator = new Estimator();
				estimator.init(option);
				estimator.estimate();
			}
			else if (option.inf){
				Inferencer inferencer = new Inferencer();
				inferencer.init(option);
				
				Model newModel = inferencer.inference();
			
				for (int i = 0; i < newModel.phi.length; ++i){
					//phi: K * V
					System.out.println("-----------------------\ntopic" + i  + " : ");
					for (int j = 0; j < 10; ++j){
						System.out.println(inferencer.globalDict.id2word.get(j) + "\t" + newModel.phi[i][j]);
					}
				}
			}
		}
		catch (CmdLineException cle){
			System.out.println("Command line error: " + cle.getMessage());
			showHelp(parser);
			return;
		}
		catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
	
	public static void showHelp(CmdLineParser parser){
		System.out.println("LDA [options ...] [arguments...]");
		parser.printUsage(System.out);
	}
	
}
