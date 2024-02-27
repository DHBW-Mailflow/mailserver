package de.dhbw.karlsruhe.students.mailflow.core.application.imap.james;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.logging.impl.Jdk14Logger;
import org.apache.james.core.Username;
import org.apache.james.mailbox.MailboxManager;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.MessageManager;
import org.apache.james.mailbox.exception.AnnotationException;
import org.apache.james.mailbox.exception.MailboxException;
import org.apache.james.mailbox.model.Mailbox;
import org.apache.james.mailbox.model.MailboxACL;
import org.apache.james.mailbox.model.MailboxACL.ACLCommand;
import org.apache.james.mailbox.model.MailboxACL.EntryKey;
import org.apache.james.mailbox.model.MailboxACL.Rfc4314Rights;
import org.apache.james.mailbox.model.MailboxACL.Right;
import org.apache.james.mailbox.model.MailboxAnnotation;
import org.apache.james.mailbox.model.MailboxAnnotationKey;
import org.apache.james.mailbox.model.MailboxId;
import org.apache.james.mailbox.model.MailboxMetaData;
import org.apache.james.mailbox.model.MailboxPath;
import org.apache.james.mailbox.model.MessageId;
import org.apache.james.mailbox.model.MessageId.Factory;
import org.apache.james.mailbox.model.MessageRange;
import org.apache.james.mailbox.model.MultimailboxesSearchQuery;
import org.apache.james.mailbox.model.ThreadId;
import org.apache.james.mailbox.model.search.MailboxQuery;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import org.apache.commons.logging.Log;

public class JamesMailboxManager implements MailboxManager {

    private Log logger;

    public JamesMailboxManager(Jdk14Logger logger) {
        this.logger = logger;
    }

    @Override
    public void startProcessingRequest(MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startProcessingRequest'");
    }

    @Override
    public void endProcessingRequest(MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endProcessingRequest'");
    }

    @Override
    public boolean hasRight(MailboxPath mailboxPath, Right right, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasRight'");
    }

    @Override
    public Publisher<Boolean> hasRightReactive(MailboxPath mailboxPath, Right right, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasRightReactive'");
    }

    @Override
    public boolean hasRight(Mailbox mailbox, Right right, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasRight'");
    }

    @Override
    public boolean hasRight(MailboxId mailboxId, Right right, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasRight'");
    }

    @Override
    public List<Rfc4314Rights> listRights(MailboxPath mailboxPath, EntryKey identifier, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listRights'");
    }

    @Override
    public List<Rfc4314Rights> listRights(Mailbox mailbox, EntryKey identifier, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listRights'");
    }

    @Override
    public MailboxACL listRights(MailboxPath mailboxPath, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listRights'");
    }

    @Override
    public MailboxACL listRights(MailboxId mailboxId, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listRights'");
    }

    @Override
    public Rfc4314Rights myRights(MailboxPath mailboxPath, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'myRights'");
    }

    @Override
    public Publisher<Rfc4314Rights> myRightsReactive(MailboxPath mailboxPath, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'myRightsReactive'");
    }

    @Override
    public Publisher<Rfc4314Rights> myRights(MailboxId mailboxId, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'myRights'");
    }

    @Override
    public Rfc4314Rights myRights(Mailbox mailbox, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'myRights'");
    }

    @Override
    public void applyRightsCommand(MailboxPath mailboxPath, ACLCommand mailboxACLCommand, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyRightsCommand'");
    }

    @Override
    public Publisher<Void> applyRightsCommandReactive(MailboxPath mailboxPath, ACLCommand mailboxACLCommand,
            MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyRightsCommandReactive'");
    }

    @Override
    public void applyRightsCommand(MailboxId mailboxId, ACLCommand mailboxACLCommand, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyRightsCommand'");
    }

    @Override
    public void setRights(MailboxPath mailboxPath, MailboxACL mailboxACL, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRights'");
    }

    @Override
    public void setRights(MailboxId mailboxId, MailboxACL mailboxACL, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRights'");
    }

    @Override
    public List<MailboxAnnotation> getAllAnnotations(MailboxPath mailboxPath, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAnnotations'");
    }

    @Override
    public Publisher<MailboxAnnotation> getAllAnnotationsReactive(MailboxPath mailboxPath, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllAnnotationsReactive'");
    }

    @Override
    public List<MailboxAnnotation> getAnnotationsByKeys(MailboxPath mailboxPath, MailboxSession session,
            Set<MailboxAnnotationKey> keys) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeys'");
    }

    @Override
    public Publisher<MailboxAnnotation> getAnnotationsByKeysReactive(MailboxPath mailboxPath, MailboxSession session,
            Set<MailboxAnnotationKey> keys) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeysReactive'");
    }

    @Override
    public List<MailboxAnnotation> getAnnotationsByKeysWithOneDepth(MailboxPath mailboxPath, MailboxSession session,
            Set<MailboxAnnotationKey> keys) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeysWithOneDepth'");
    }

    @Override
    public Publisher<MailboxAnnotation> getAnnotationsByKeysWithOneDepthReactive(MailboxPath mailboxPath,
            MailboxSession session, Set<MailboxAnnotationKey> keys) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeysWithOneDepthReactive'");
    }

    @Override
    public List<MailboxAnnotation> getAnnotationsByKeysWithAllDepth(MailboxPath mailboxPath, MailboxSession session,
            Set<MailboxAnnotationKey> keys) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeysWithAllDepth'");
    }

    @Override
    public Publisher<MailboxAnnotation> getAnnotationsByKeysWithAllDepthReactive(MailboxPath mailboxPath,
            MailboxSession session, Set<MailboxAnnotationKey> keys) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnnotationsByKeysWithAllDepthReactive'");
    }

    @Override
    public void updateAnnotations(MailboxPath mailboxPath, MailboxSession session,
            List<MailboxAnnotation> mailboxAnnotations) throws MailboxException, AnnotationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAnnotations'");
    }

    @Override
    public Publisher<Void> updateAnnotationsReactive(MailboxPath mailboxPath, MailboxSession session,
            List<MailboxAnnotation> mailboxAnnotations) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAnnotationsReactive'");
    }

    @Override
    public MailboxSession createSystemSession(Username userName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSystemSession'");
    }

    @Override
    public AuthorizationStep authenticate(Username givenUserid, String passwd) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public AuthorizationStep authenticate(Username givenUserid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }

    @Override
    public EnumSet<MailboxCapabilities> getSupportedMailboxCapabilities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSupportedMailboxCapabilities'");
    }

    @Override
    public boolean hasCapability(MailboxCapabilities capability) {
        this.logger.info("hasCapability(" + capability + ") -> false");
        return false;
    }

    @Override
    public EnumSet<MessageCapabilities> getSupportedMessageCapabilities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSupportedMessageCapabilities'");
    }

    @Override
    public EnumSet<SearchCapabilities> getSupportedSearchCapabilities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSupportedSearchCapabilities'");
    }

    @Override
    public MessageManager getMailbox(MailboxPath mailboxPath, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailbox'");
    }

    @Override
    public MessageManager getMailbox(MailboxId mailboxId, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailbox'");
    }

    @Override
    public Publisher<MessageManager> getMailboxReactive(MailboxId mailboxId, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailboxReactive'");
    }

    @Override
    public Publisher<MessageManager> getMailboxReactive(MailboxPath mailboxPath, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailboxReactive'");
    }

    @Override
    public MessageManager getMailbox(Mailbox mailbox, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMailbox'");
    }

    @Override
    public Optional<MailboxId> createMailbox(MailboxPath mailboxPath, CreateOption createOption,
            MailboxSession mailboxSession) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMailbox'");
    }

    @Override
    public void deleteMailbox(MailboxPath mailboxPath, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMailbox'");
    }

    @Override
    public Mailbox deleteMailbox(MailboxId mailboxId, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMailbox'");
    }

    @Override
    public List<MailboxRenamedResult> renameMailbox(MailboxPath from, MailboxPath to, RenameOption option,
            MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renameMailbox'");
    }

    @Override
    public List<MailboxRenamedResult> renameMailbox(MailboxId mailboxId, MailboxPath newMailboxPath,
            RenameOption option, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renameMailbox'");
    }

    @Override
    public List<MessageRange> copyMessages(MessageRange set, MailboxPath from, MailboxPath to, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyMessages'");
    }

    @Override
    public List<MessageRange> copyMessages(MessageRange set, MailboxId from, MailboxId to, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyMessages'");
    }

    @Override
    public List<MessageRange> moveMessages(MessageRange set, MailboxPath from, MailboxPath to, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveMessages'");
    }

    @Override
    public List<MessageRange> moveMessages(MessageRange set, MailboxId from, MailboxId to, MailboxSession session)
            throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveMessages'");
    }

    @Override
    public Flux<MailboxMetaData> search(MailboxQuery expression, MailboxSearchFetchType fetchType,
            MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public Publisher<MessageId> search(MultimailboxesSearchQuery expression, MailboxSession session, long limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public Publisher<MessageId> getThread(ThreadId threadId, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getThread'");
    }

    @Override
    public Publisher<Boolean> mailboxExists(MailboxPath mailboxPath, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mailboxExists'");
    }

    @Override
    public List<MailboxPath> list(MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    @Override
    public boolean hasChildren(MailboxPath mailboxPath, MailboxSession session) throws MailboxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasChildren'");
    }

    @Override
    public Publisher<Boolean> hasChildrenReactive(MailboxPath mailboxPath, MailboxSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasChildrenReactive'");
    }

    @Override
    public Factory getMessageIdFactory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageIdFactory'");
    }

}
