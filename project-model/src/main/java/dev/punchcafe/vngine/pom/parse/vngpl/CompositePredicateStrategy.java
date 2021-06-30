package dev.punchcafe.vngine.pom.parse.vngpl;

import dev.punchcafe.vngine.pom.InvalidVngplExpression;
import dev.punchcafe.vngine.pom.model.vngpl.PredicateExpression;
import dev.punchcafe.vngine.pom.model.vngpl.composite.AndOrOperation;
import dev.punchcafe.vngine.pom.model.vngpl.composite.CompositeExpression;
import dev.punchcafe.vngine.pom.model.vngpl.composite.PredicateLink;

import java.util.ArrayList;
import java.util.List;

public class CompositePredicateStrategy implements ParsingStrategy {

    private static String AND_JOINER = " AND ";
    private static String OR_JOINER = " OR ";

    @Override
    public PredicateExpression parse(String message, PredicateParser predicateParser) {
        final String trimmedMessage = message.trim();
        final List<PredicateLink> links = new ArrayList<>();
        int bracketScope = 0;
        Integer linkStart = null;
        AndOrOperation currentJoinOperation = null;
        int i = 0;
        while (i < message.length()) {
            if (message.charAt(i) == '(') {
                bracketScope++;
            } else if (message.charAt(i) == ')') {
                bracketScope--;
            } else if (message.substring(i).startsWith(AND_JOINER) && bracketScope == 0) {
                if (linkStart != null) {
                    links.add(PredicateLink.newLink(predicateParser.parse(message.substring(linkStart, i)), currentJoinOperation));
                } else {
                    links.add(PredicateLink.firstLink(predicateParser.parse(message.substring(0, i))));
                }
                currentJoinOperation = AndOrOperation.AND;
                linkStart = i + AND_JOINER.length();
                i = i + AND_JOINER.length();
                continue;
            } else if (message.substring(i).startsWith(OR_JOINER) && bracketScope == 0) {
                if (linkStart != null) {
                    links.add(PredicateLink.newLink(predicateParser.parse(message.substring(linkStart, i)), currentJoinOperation));
                } else {
                    links.add(PredicateLink.firstLink(predicateParser.parse(message.substring(0, i))));
                }
                currentJoinOperation = AndOrOperation.OR;
                linkStart = i + OR_JOINER.length();
                i = i + OR_JOINER.length();
                continue;
            }
            i++;
        }
        if(!links.isEmpty()){
            links.add(PredicateLink.newLink(predicateParser.parse(message.substring(linkStart)), currentJoinOperation));
            return CompositeExpression.fromLinks(links);
        } else if(trimmedMessage.startsWith("(") && trimmedMessage.endsWith(")")){
            return parse(trimmedMessage.substring(1,trimmedMessage.length()-1), predicateParser);
        }
        throw new InvalidVngplExpression();
    }

    @Override
    public boolean isApplicable(String message) {
        return message.trim().startsWith("(") || message.contains(" AND ") || message.contains(" OR ");
    }

    @Override
    public Integer priority() {
        return 0;
    }
}
