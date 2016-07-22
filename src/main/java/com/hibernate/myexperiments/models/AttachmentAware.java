package com.hibernate.myexperiments.models;

import java.util.List;

public interface AttachmentAware<T extends Attachment> {
    List<Attachment> getAttachments();
    void addAttachments(final List<Attachment> newAttachments);
    void deleteAttachments(final List<Attachment> delmes);
}
